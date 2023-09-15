package com.shenxi.psych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author shenxi
 * @Date 2023/9/24 23:00
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Integer id;

    private String nickName;

    private Integer userId;

    private Long gmtCreate;

    private Long gmtModified;
}
