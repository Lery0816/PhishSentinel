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
@TableName("whois_info")
public class WhoisInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String domainName;
    private String registrar;
    private LocalDate creationDate;
    private Boolean privacyProtection;
}
