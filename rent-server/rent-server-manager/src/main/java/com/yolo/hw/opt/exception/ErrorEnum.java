package com.yolo.hw.opt.exception;


import com.yolo.exception.IError;

import java.util.HashMap;
import java.util.Map;

public enum ErrorEnum implements IError {

    //系统错误定义==============
    BUSINESS_ERR(601, "未定义错误"),
    //参数错误
    PARAM_ERR(602, "参数错误"),
    //610--------629用户错误定义
    USR_NOT_ERR(615, "用户不存在"),

    NO_DEVICES_ERR(650, "设备信息不存在，请检查"),
    NO_DEVICE_ERR(650, "车机不存在"),
    NO_CAR_ERR(650, "车辆不存在"),
    NO_INFO_ERR(650, "退回车辆信息不存在"),
    CAR_NOT_MATCH_ERR(650, "订单车辆数与导入数量不匹配"),
    CAR_NUM_NOT_MATCH_ERR(650, "出库车辆数大于租赁订单下单车辆数，请核实后再试"),
    NO_ORDER_ERR(650, "订单信息不存在"),
    OPT_CAR_ERR(650, "部分车辆不存在，请您重新选择"),
    NO_REMOTE_DEVICE_ERR(650, "远程查询车机信息出错"),
    NO_DEVICE_OUT_ERR(650, "出库到项目失败"),
    NO_DEVICE_BACK_ERR(650, "车机回收失败失败"),
    NOT_EXCEL_FILE_ERR(690, "导入文件非EXCEL"),
    ;
    private int code;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    ErrorEnum(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String,Object> getMsg() {
        Map<String,Object> map=new HashMap<>();
        map.put("msg",info);
        return map;
    }

    public static ErrorEnum getErrorEnum(int code) {
        ErrorEnum[] errorEnums = ErrorEnum.values();
        for (ErrorEnum errorEnum : errorEnums) {
            if (code == errorEnum.getCode()) {
                return errorEnum;
            }
        }
        return BUSINESS_ERR;
    }
}
