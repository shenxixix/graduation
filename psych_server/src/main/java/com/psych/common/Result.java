package com.psych.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接口调用通用返回体
 * @author shenxi
 * @date 2023/09/10 17:17
 */
@Data
@ApiModel("接口调用通用返回体")
public class Result<T> {
    @ApiModelProperty(value = "成功标志(true:成功, false失败)")
    private Boolean success;
    @ApiModelProperty(value = "错误码(成功：200, 本地业务异常：-1, 其他由被调用方返回)")
    private String code;
    @ApiModelProperty(value = "错误信息描述")
    private String message;
    @ApiModelProperty(value = "数据（success=true时才可能有值）")
    private T data;

    /**
     * 成功，无数据
     */
    public static <T> Result<T> success(){
        return success(null);
    }

    /**
     * 成功，有数据
     * @param data 数据
     */
    public static <T> Result<T> success(T data){
        Result<T> result =  new Result<>();
        result.setSuccess(true);
        result.setCode("200");
        result.setMessage("OK");
        result.setData(data);
        return result;
    }

    /**
     * 失败，本地业务异常
     * @param message 错误描述
     */
    public static <T> Result<T> failure(String message){
        return failure("-1", message);
    }

    /**
     * 失败，被调用方异常
     * @param code 错误码
     * @param message 错误描述
     */
    public static <T> Result<T> failure(Number code, String message){
        return failure(String.valueOf(code), message);
    }

    /**
     * 失败，被调用方异常
     * @param code 错误码
     * @param message 错误描述
     */
    public static <T> Result<T> failure(String code, String message){
        Result<T> result =  new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        result.setData(null);
        return result;
    }
}
