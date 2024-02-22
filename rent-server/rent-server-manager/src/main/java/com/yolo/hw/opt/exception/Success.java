package com.yolo.hw.opt.exception;

public class Success {
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Success(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Success ok() {
        return new Success(0, "成功");
    }

    public static Success fail() {
        return new Success(1, "失败");
    }
}
