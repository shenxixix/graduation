package com.shenxi.psych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author shenxi
 * @Date 2023/9/24 22:08
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    private Integer id;

    private Integer accountId;

    private String accountName;

    private String password;

    private Long gmtCreate;

    private Long gmtModified;
}
