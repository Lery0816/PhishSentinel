package com.uts.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("domain_list")
public class DomainList {
    private Integer id;
    private String domainName;
    private LocalDateTime createdAt;
}
