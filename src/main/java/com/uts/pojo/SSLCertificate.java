package com.uts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ssl_certificate")
public class SSLCertificate {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String domainName;
    private String certificateIssuer;
    private String certificateType; // EV/OV/DV/other
    private Boolean certificateValid;
    private LocalDate expirationDate;
}
