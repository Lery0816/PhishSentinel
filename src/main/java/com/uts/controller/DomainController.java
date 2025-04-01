package com.uts.controller;

import com.uts.pojo.RiskResult;
import com.uts.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.uts.pojo.Domain;

@Controller
@ResponseBody
@RequestMapping("/api/domain")
public class DomainController {
    @Autowired
    private DomainService domainService;

    @PostMapping("/check")
    public RiskResult checkDomain(@RequestBody Domain domain) {
        String domainStr = domain.getDomainName();
        RiskResult riskResult = new RiskResult();
        riskResult.setListScore(domainService.checkList(domainStr));
        riskResult.setStructureScore(domainService.analyzeDomainStructure(domainStr));
        riskResult.setSslScore(domainService.calculateSSLScore(domainStr));
        domainService.checkWhoisInfo(domainStr);
        riskResult.setWhoisScore(domainService.calculateWhoisScore(domainStr));
        riskResult.calculateRiskLevel();
        return riskResult;
    }

    @GetMapping("/score")
    public RiskResult scoreCheck(@RequestParam String domain) {
        RiskResult riskResult = new RiskResult();
        riskResult.setListScore(domainService.checkList(domain));
        riskResult.setStructureScore(domainService.analyzeDomainStructure(domain));
        riskResult.setSslScore(domainService.calculateSSLScore(domain));
        domainService.checkWhoisInfo(domain);
        riskResult.setWhoisScore(domainService.calculateWhoisScore(domain));
        riskResult.calculateRiskLevel();
        return riskResult;
    }
}
