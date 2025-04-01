package com.uts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ssl_certificate")
public class SSLCertificate {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("domain_name")
    private String domainName;

    @TableField("certificate_issuer")
    private String certificateIssuer;

    @TableField("certificate_type")
    private String certificateType; // EV/OV/DV/other

    @TableField("certificate_valid")
    private Boolean certificateValid;

    @TableField("expiration_date")
    private LocalDate expirationDate;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
