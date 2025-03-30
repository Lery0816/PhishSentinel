package com.uts.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("whitelist")
public class WhiteList {
    @TableId
    private Integer id;
    @TableField("domain_name")
    private String domainName;
    @TableField("added_at")
    private LocalDateTime addedAt;
}
