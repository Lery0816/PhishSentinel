package com.uts;

import com.uts.pojo.SSLCertificate;
import com.uts.service.impl.DomainServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DomainServiceImplTest {
    @Autowired
    private DomainServiceImpl domainService;

    @Test
    public void testCheckSSL() {
        String domain = "example.com";
        SSLCertificate result = domainService.checkSSL(domain);
        System.out.println("SSL检测结果：" + result);
    }
    @Test
    public void testcheckWhoisInfo() {
        String domain = "example.com";
        domainService.checkWhoisInfo(domain);
        System.out.println("Whois检测结束");
    }
}
