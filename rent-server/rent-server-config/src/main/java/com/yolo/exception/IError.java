package com.yolo.exception;

import java.util.Map;

/**
 * 功能描述
 *
 * @author:晏枭
 * @Date:2019/9/2314:37
 */
public interface IError {

    String getInfo();

    int getCode();

    Map<String, Object> getMsg();
}
