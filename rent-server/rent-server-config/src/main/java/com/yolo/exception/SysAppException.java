package com.yolo.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SysAppException extends RuntimeException {
    private int code;
    private String info;
    public SysAppException(IError iError){
        super(iError.getInfo());
        this.code = iError.getCode();
        this.info = iError.getInfo();
    }
    public SysAppException(int code, String info) {
        super(info);
        this.code = code;
        this.info = info;
    }

    public Map<String,Object> getMsg() {
        Map<String,Object> map=new HashMap<>();
        map.put("msg",info);
        return map;
    }
}
