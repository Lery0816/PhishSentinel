package com.uts.service;

import com.uts.pojo.Domain;

public interface DomainService {
    Domain checkDomain(String domainName);
    boolean checkSSL(String domainName);
    int calculateRiskScore(String domainName);
}
