package com.uts.pojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("domain")
public class Domain {
    private Long id;
    private String domainName;
    private String expiryDate;
    private String certificateInfo;
    private int score;
}

