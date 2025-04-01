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
@TableName("whois_info")
public class WhoisInfo {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("domain_name")
    private String domainName;

    private String registrar;

    @TableField("creation_date")
    private LocalDate creationDate;

    @TableField("privacy_protection")
    private Boolean privacyProtection;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
