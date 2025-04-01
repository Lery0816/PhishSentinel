package com.uts.controller;

import com.uts.pojo.RiskResult;
import com.uts.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api/domain")
public class DomainController {
    @Autowired
    private DomainService domainService;

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
