package com.uts.controller;

import com.uts.enums.RiskLevel;
import com.uts.pojo.RiskResult;
import com.uts.pojo.SSLCertificate;
import com.uts.pojo.WhoisInfo;
import com.uts.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.uts.pojo.Domain;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class DomainController {
    @Autowired
    private DomainService domainService;

    @GetMapping("/score")
    @ResponseBody
    public RiskResult coreCheck(@RequestParam String domain) {
        RiskResult riskResult =new RiskResult();
        riskResult.setListScore(domainService.checkList(domain));//获取黑白名单分数
        riskResult.setStructureScore(domainService.analyzeDomainStructure(domain));//获取域名结构分数
        riskResult.setSslScore(domainService.calculateSSLScore(domain));//获取ssl证书分数
        domainService.checkWhoisInfo(domain);//更新whois信息
        riskResult.setWhoisScore(domainService.calculateWhoisScore(domain));//获取whois分数
        riskResult.calculateRiskLevel();
        return riskResult;
    }
//    @GetMapping("/ssl")
//    @ResponseBody
//    public SSLCertificate getSSLCertificateInfo(@RequestParam String domain){
//        return domainService.getSSLCertificateInfo(domain);
//    }
//
//    @GetMapping("/Whois")
//    @ResponseBody
//    public WhoisInfo getWhoisInfo(@RequestParam String domain){
//        return domainService.getWhoisInfo(domain);
//    }
}
