package com.yolo.exception;

import java.util.HashMap;
import java.util.Map;

public enum ErrorEnum implements IError {
    //系统错误定义==============
    BUSINESS_ERR(601, "未定义错误"),
    //参数错误
    PARAM_ERR(602, "参数错误"),
    NET_ERR(603, "网络错误"),
    AUTH_ERR(604, "无效用户"),
    AUTH_LOGIN_ERR(605, "用户上线唯一性"),
    TOKEN_ERR(606, "用户无权限"),
    HEAD_PARAM_ERR(607, "无法获取APP版本信息"),
    GATEWAY_ERR(608, "网关错误"),
    CONFIG_ERR(609, "环境配置错误"),
    API_ERR(610, "API错误"),
    E_SIGN_ERR(611, "e签保接口异常"),
    //不要在以下加枚举。在各自系统里面增加业务枚举，这里为系统框架所用
    ;
    ErrorEnum(int code, String info) {
        this.code = code;
        this.info = info;
    }
    private int code;
    private String info;

    public String getInfo() {
        return info;
    }
    public int getCode() {
        return code;
    }

    public Map<String, Object> getMsg() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", info);
        return map;
    }

}
