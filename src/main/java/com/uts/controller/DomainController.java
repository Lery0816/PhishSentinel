package com.uts.controller;

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

    @GetMapping("/check-domain")
    public Domain checkDomain(@RequestParam String domain) {
        return domainService.checkDomain(domain);
    }

    @GetMapping("/check-ssl")
    public boolean checkSSL(@RequestParam String domain) {
        return domainService.checkSSL(domain);
    }

    @GetMapping("/calculate-score")
    public int calculateScore(@RequestParam String domain) {
        return domainService.calculateRiskScore(domain);
    }
}
