package com.uts.service;

import com.uts.pojo.BlackList;
import com.uts.pojo.SSLCertificate;
import com.uts.pojo.WhoisInfo;

import java.util.List;

public interface DomainService {

    Boolean isInBlacklist(String domainName);

    Boolean isInWhitelist(String domainName);

    int checkList(String domainName);

    int analyzeDomainStructure(String domain);


    SSLCertificate checkSSL(String domainName);

    SSLCertificate getSSLCertificateInfo(String domain);

    int calculateSSLScore(String domain);




    void checkWhoisInfo(String domain);

    WhoisInfo getWhoisInfo(String domain);

    int calculateWhoisScore(String domain);


}
