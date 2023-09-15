package com.shenxi.psych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author shenxi
 * @Date 2023/10/4 17:09
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    Integer id;
    String name;
    private Integer gender;
    private Integer age;
    private String tel;
    private String email;
    String password;
}
