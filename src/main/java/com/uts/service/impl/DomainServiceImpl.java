package com.uts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uts.mapper.BlacklistMapper;
import com.uts.mapper.SSLCertificateMapper;
import com.uts.mapper.WhitelistMapper;
import com.uts.mapper.WhoisInfoMapper;
import com.uts.pojo.*;
import com.uts.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class DomainServiceImpl implements DomainService {
    @Autowired
    WhitelistMapper whitelistMapper;
    @Autowired
    BlacklistMapper blacklistMapper;
    @Autowired
    SSLCertificateMapper SSLCertificateMapper;
    @Autowired
    WhoisInfoMapper whoisInfoMapper;

    private static final String API_KEY = "at_QN8suVyFHbeHcQr3kM3hr1qR83Vem";
    @Override
    public int checkList(String domainName) {
        if (isInBlacklist(domainName)) {
            return 100; // 在黑名单中直接判定高风险100
        } else if (isInWhitelist(domainName)) {
            return 0; // 在白名单中直接判定安全0
        } else {
            return 15; // 未知域名，初步判定15
        }
    }

    @Override
    public int analyzeDomainStructure(String domain) {
        int score = 0;
        //长度检测
        if (domain.length() > 50) {
            score += 10;
        } else if (domain.length() > 30) {
            score += 5;
        }
        //特殊字符检测
        if (domain.matches(".*[-_%$'\";].*")) {
            score += 5;
        }
        if (domain.matches(".*\\d{4,}.*") || domain.contains("--")) {
            score += 5;
        }
        //伪字符检测
        String[] phishingPatterns = { "rn", "vv", "micr0soft", "g00gle", "faceb00k", "paypa1", "amaz0n", "banka1", "1ogin", "secur1ty",
                "acc0unt", "ver1fy", "updat3", "conf1rm", "supp0rt", "serv1ce", "re1ease"};
        for (String pattern : phishingPatterns) {
            if (domain.toLowerCase().contains(pattern)) {
                score += 10;
                break;
            }
        }
        //SQL注入检测
        String[] sqlPatterns = {"or 1=1", "union select", "drop table", ";--"};
        for (String pattern : sqlPatterns) {
            if (domain.toLowerCase().contains(pattern)) {
                score += 10;
                break;
            }
        }
        return score;
    }

    @Override
    public SSLCertificate checkSSL(String domainName) {
        // Simulate SSL certificate verification
        SSLCertificate ssl = new SSLCertificate();
        ssl.setDomainName(domainName);
        try {
            //拼接url
            URL url = new URL("https://" + domainName);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.connect();
            X509Certificate cert = (X509Certificate) conn.getServerCertificates()[0];
            ssl = new SSLCertificate();
            ssl.setDomainName(domainName);
            ssl.setCertificateIssuer(cert.getIssuerX500Principal().getName());

            // 证书类型简单模拟
            String issuer = cert.getIssuerX500Principal().getName().toLowerCase();
            if (issuer.contains("digicert") || issuer.contains("globalsign")) {
                ssl.setCertificateType("EV");
            } else if (issuer.contains("let's encrypt")) {
                ssl.setCertificateType("DV");
            } else {
                ssl.setCertificateType("other");
            }

            ssl.setCertificateValid(cert.getNotAfter().toInstant().isAfter(java.time.Instant.now()));
            ssl.setExpirationDate(cert.getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            SSLCertificateMapper.insert(ssl);
        } catch (Exception e) {
            // 如果获取失败，插入无效数据
            ssl.setCertificateIssuer("Unknown");
            ssl.setCertificateType("None");
            ssl.setCertificateValid(false);
            ssl.setExpirationDate(null);
        }
        QueryWrapper<SSLCertificate> wrapper = new QueryWrapper<>();
        wrapper.eq("domain_name", domainName);
        SSLCertificate existing = SSLCertificateMapper.selectOne(wrapper);
        if (existing!= null) {
            ssl.setId(existing.getId());
            SSLCertificateMapper.updateById(ssl);// 设置ID进行更新
        }else {
            SSLCertificateMapper.insert(ssl);
        }
        return ssl;
    }

    @Override
    public int calculateSSLScore(String domain) {
        SSLCertificate ssl = getSSLCertificateInfo(domain);
        if (ssl == null) {
            return 40; // 没有证书信息
        }
        if (!ssl.getCertificateValid() || "None".equals(ssl.getCertificateType())) {
            return 30;//证书无效或者证书类型为None
        }
        if ("DV".equals(ssl.getCertificateType())) {
            return 20;
        }
        if ("OV".equals(ssl.getCertificateType())) {
            return 10;
        }
        return 0; // EV
    }

    @Override
    public int calculateWhoisScore(String domain) {
        WhoisInfo whois = getWhoisInfo(domain);
        if (whois == null) {
            return 30; // 无whois信息，高风险30
        }
        //计算有效期
        LocalDate now = LocalDate.now();
        int months = (now.getYear() - whois.getCreationDate().getYear()) * 12
                + (now.getMonthValue() - whois.getCreationDate().getMonthValue());
        if (months < 3) {
            return 30;
        } else if (months < 12) {
            return whois.getPrivacyProtection() ? 20 : 15;
        } else if (months < 36) {
            return whois.getPrivacyProtection() ? 10 : 0;
        } else {
            return 0;
        }
    }

    @Override
    public Boolean isInBlacklist(String domainName) {
        QueryWrapper<BlackList> wrapper = new QueryWrapper<>();
        wrapper.eq("domain_Name",domainName);
        return blacklistMapper.selectCount(wrapper)>0;
    }

    @Override
    public Boolean isInWhitelist(String domainName) {
        QueryWrapper<WhiteList> wrapper = new QueryWrapper<>();
        wrapper.eq("domain_Name",domainName);
        return whitelistMapper.selectCount(wrapper)>0;
    }

    @Override
    public SSLCertificate getSSLCertificateInfo(String domain) {
        QueryWrapper queryWrapper=new QueryWrapper<SSLCertificate>();
        queryWrapper.eq("domain_name", domain);
        return SSLCertificateMapper.selectOne(queryWrapper);
    }

    @Override
    public void checkWhoisInfo(String domain) {
        try {
            String apiUrl = "https://www.whoisxmlapi.com/whoisserver/WhoisService?apiKey=" + API_KEY
                    + "&domainName=" + domain + "&outputFormat=JSON";

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //使用Jackson解析
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.toString());
            JsonNode whoisRecord = root.path("WhoisRecord");
            WhoisInfo whois = new WhoisInfo();
            whois.setDomainName(domain);
            whois.setRegistrar(whoisRecord.path("registrarName").asText(null));
            String createdDate = whoisRecord.path("createdDate").asText(null);
            if (createdDate != null) {
                LocalDate date = LocalDate.parse(createdDate.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                whois.setCreationDate(date);
            }

            boolean privacy = whoisRecord.path("privacy").asBoolean(false);
            whois.setPrivacyProtection(privacy);

            //更新数据库
            QueryWrapper<WhoisInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("domain_name", domain);
            WhoisInfo existing = whoisInfoMapper.selectOne(wrapper);
            if (existing != null) {
                whois.setId(existing.getId());
                whoisInfoMapper.updateById(whois);//存在更新
            } else {
                whoisInfoMapper.insert(whois);//不存在插入
            }

        } catch (Exception e) {
            System.err.println("WhoisAPI获取内容失败: " + e.getMessage());
        }
    }

    @Override
    public WhoisInfo getWhoisInfo(String domain) {
        QueryWrapper<WhoisInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("domain_name", domain);
        return whoisInfoMapper.selectOne(wrapper);
    }
}
