package com.yolo.hw.common.util.jwt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @Author:shenxi
 * @Des: 用户信息
 * @Date:2019/2/28
 */
@Data
public class Usr {
    @ApiModelProperty(notes = "用户ID")
    private String userId;
    @ApiModelProperty(notes = "登录/签发时间")
    private Long iat;
    @ApiModelProperty(notes = "用户登录时区")
    private Integer zone;
    @ApiModelProperty(notes = "用户权限值组")
    private Set<Integer> perms;
}
