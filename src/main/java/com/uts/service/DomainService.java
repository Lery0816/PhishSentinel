package com.uts.service;

import com.uts.pojo.SSLCertificate;
import com.uts.pojo.WhoisInfo;

public interface DomainService {
    //检查是否命中黑名单
    Boolean isInBlacklist(String domainName);
    //检查是否命中白名单
    Boolean isInWhitelist(String domainName);
    //检查是否命中名单以及计算名单分数
    int checkList(String domainName);

    //域名结构分析
    int analyzeDomainStructure(String domain);


    //检查SSL证书信息并更新数据库
    SSLCertificate checkSSL(String domainName);
    //从数据库中获取SSL证书信息
    SSLCertificate getSSLCertificateInfo(String domain);
    //计算SSL证书分数
    int calculateSSLScore(String domain);



    //检查whois证书信息并更新数据库
    void checkWhoisInfo(String domain);
    //从数据库中获取Whois信息
    WhoisInfo getWhoisInfo(String domain);
    //计算Whois分数
    int calculateWhoisScore(String domain);
}
