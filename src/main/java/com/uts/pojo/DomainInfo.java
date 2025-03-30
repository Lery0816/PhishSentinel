package com.uts.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("domainInfo")
public class DomainInfo {
    private String domainName;
    private boolean inWhitelist;
    private boolean inBlacklist;
    private boolean hasSSL;
    private String certificateIssuer;//Issuer of the SSL certificate
    private String certificateType;//Type of the SSL certificate
    private boolean certificateValid;//Whether the SSL certificate is valid
    private String whoisRegistrar;//Domain name registration authority
    private String whoisCreationDate;//The registration date of the domain name
    private boolean whoisPrivacyProtection;//Indicates whether privacy protection is enabled in WHOIS information
}
