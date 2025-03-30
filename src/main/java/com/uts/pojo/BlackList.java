package com.uts.pojo;

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
@TableName("blacklist")
public class BlackList {
    private Integer id;
    @TableField("domain_name")
    private String domainName;
    private String reason;
    @TableField("added_at")
    private LocalDateTime addedAt;
}
