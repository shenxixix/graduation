package com.yolo.hw.opt.exception;

import com.yolo.exception.IError;

import java.util.HashMap;
import java.util.Map;

public enum ErrorsInfo implements IError {
    //系统错误定义==============

    //不要在以下加枚举。在各自系统里面增加业务枚举，这里为系统框架所用
    //610--------629用户错误定义
    BUSINESS_ERR(601, "业务异常"),
    USR_LOGIN_ERR(631, "帐号或者密码错误"),
    USR_DISABLE_ERR(610, "帐号已经被禁用"),
    USR_CODE_USE_ERR(611, "您当天获取验证码的条数已用完，请24小时后再试。"),
    USR_CODE_GET_ERR(612, "您输入的验证码不正确，请重新输入"),
    USR_CODE_ERR(613, "您输入的验证码不正确，请重新输入"),
    USR_MULTI_ERR(614, "用户已经注册"),
    USR_NOT_ERR(615, "用户不存在"),
    USR_PUSHTOKEN_ERR(616, "更新推送Token出错"),
    USR_PHONE_ERR(617, "手机号格式错误"),
    USR_CARD_ERR(618, "身份证格式错误"),
    USR_MILE_ERR(619, "仪表里程数超过上限"),
    USR_PWD_NOT_EQUAL_ERR(620, "两次输入的密码不一致"),
    USR_CODE_NULL_ERR(621, "验证码不能为空"),
    USER_PHONE_TYPE(623, "没有获取到手机登陆类型"),
    USER_PHONE_ZONE(624, "没有获取到手机登陆时区"),
    USER_LOCK(679,"已连续5次密码验证码错误，请5分钟后再登录"),
    USER_QRCODE_OUT_TIME(680,"登录二维码过期，请重新扫码"),
    USER_QRCODE_LOG(681,"有效期后还未登录，二维码需刷新后才可使用"),
    ;

    ErrorsInfo(int code, String info) {
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
