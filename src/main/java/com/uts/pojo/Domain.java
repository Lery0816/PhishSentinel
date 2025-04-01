package com.uts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("domain")
public class Domain {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("domain_name")
    private String domainName;

    @TableField("expiry_date")
    private String expiryDate;

    @TableField("certificate_info")
    private String certificateInfo;

    private Integer score;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
