package com.uts.service.impl;

import com.uts.pojo.Domain;
import com.uts.service.DomainService;
import org.springframework.stereotype.Service;

@Service
public class DomainServiceImpl implements DomainService {

    @Override
    public Domain checkDomain(String domainName) {
        // Domain name check
        Domain domain=new Domain();
        domain.setDomainName(domainName);
        domain.setExpiryDate("2025-12-01");
        domain.setCertificateInfo("Effective");
        return domain;
    }

    @Override
    public boolean checkSSL(String domainName) {
        // Simulate SSL certificate verification
        return domainName.startsWith("https");
    }

    @Override
    public int calculateRiskScore(String domainName) {
        // Scoring
        if (domainName.contains("safe")) return 0;
        if (domainName.contains("warning")) return 50;
        return 100; //test score
    }
}
