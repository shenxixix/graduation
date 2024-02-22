package com.yolo.hw.user.exception;

import com.yolo.exception.IError;

import java.util.HashMap;
import java.util.Map;

public enum ErrorsInfo implements IError {
    //系统错误定义==============

    //不要在以下加枚举。在各自系统里面增加业务枚举，这里为系统框架所用
    _ERR(611, "从611开始"),
    ERROR_VALID(612, "参数校验错误"),
    USR_NOT_ERR(613, "用户不存在"),
    SR_CODE_ERR(614, "验证码错误，请重新输入"),
    STATION_NOT_ERR(615, "站点不存在"),
    STATION_NO_USER(616, "用户与站点不匹配"),
    STATION_UPT_MANGER_ERR(616, "更换团队管理员验证码错误"),
    NOT_MANAGER(616, "只有管理员才能做次操作"),
    STATION_ORDER_EXIST(618, "请完结租赁订单后，再关闭"),
    STATION_MONEY_EXIST(618, "钱包还有未提现的金额，请体现后再关闭"),
    STATION_CARS_EXIST(618, "站点下还有车辆未退回，请退回后再关闭"),
    USR_IS_REG(613, "手机号已被注册"),
    PUSH_ERR(620, "推送服务异常"),
    TEAM_DEL_USER(623, "用户不能自己删除自己"),
    TEAM_DEL_USERLIST(625, "请先删除团队下的员工及车辆，再进行操作"),
    TEAM_OUT_ERROR(627, "团队管理员不能退出"),
    MSG_ILLEGAL_ERROR(630, "消息不允许删除"),
    STATION_APPLY_ERROR(650, "请填写租赁授信信息："),
    STATION_APPLY_STATUS_ERROR(650, "您已经提交申请，请勿重复提交"),
    USER_APPLY_STATUS_ERROR(650, "该企业已认证，请勿重复提交"),

    /*** 700 --- 720 为订单异常 **/
    USR_ORDER_ALIVE_ERR(701, "用户还存在未结束的合约"),
    ORDER_CAR_BIND_ERR(702, "该车辆暂时无法租赁，请更换其它车辆!"),
    ORDER_NO_CAR_ERR(703, "车辆不存在"),
    ORDER_CAR_MODEL_DFF_ERR(704, "车辆类型不一致"),
    ORDER_NO_START_ERR(705, "租赁合约还没有开始"),
    ORDER_CAR_STATION_DIFF(706, "站点不一致"),
    /*** 720 --- 730 为钱包异常 **/
    WALLET_MONEY_INSUFFICIENT(721, "钱包余额不足"),
    WALLET_SINGLE_WITHDRAW_ERR(722, "单笔提现超过额度"),
    WALLET_DAY_WITHDRAW_ERR(722, "当日提现超过总额度"),
    RENT_ATUH_ERR(722, "该费用已审核成功，请勿重复提交"),
    RENT_ATUH_CHECK_ERR(722, "该费用正在审核，请勿重复提交"),
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
