package com.yolo.config.normal;

import com.yolo.exception.ErrorEnum;
import com.yolo.exception.SysAppException;
import feign.RetryableException;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.ConnectException;

/**
 * @Author:shenxi
 * @Des:全局异常处理
 * @Date:2017/11/14
 */
@Log4j2
@RestControllerAdvice
public class ControllerException {

    @ExceptionHandler(value = {
            Exception.class,
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
    })
    public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        String path = request.getRequestURI();
        log.info("请求path={}异常",path, e);
        Throwable cause = e.getCause();
        if (cause instanceof ConnectException
                ||cause instanceof java.net.SocketTimeoutException
                ||cause instanceof com.netflix.client.ClientException
                ||cause instanceof RetryableException) {
            //feign调用错误
            response.setStatus(ErrorEnum.NET_ERR.getCode());
            return ErrorEnum.NET_ERR.getMsg();
        } else if (e instanceof MethodArgumentNotValidException) {
            ObjectError error = ((MethodArgumentNotValidException)e).getBindingResult().getAllErrors().get(0);
            response.setStatus(ErrorEnum.API_ERR.getCode());
            return error.getDefaultMessage();
        } else if (e instanceof IllegalArgumentException) {
            response.setStatus(ErrorEnum.PARAM_ERR.getCode());
            IllegalArgumentException exception = (IllegalArgumentException)e;
            return exception.getMessage();
        }
        response.setStatus(ErrorEnum.BUSINESS_ERR.getCode());
        return ErrorEnum.BUSINESS_ERR.getMsg();
    }

    @ExceptionHandler(value = SysAppException.class)
    public Object SysAppExceptionHandler(HttpServletRequest request, HttpServletResponse response, SysAppException e) {
        String path = request.getRequestURI();
        log.warn("业务请求path={}异常",path, e);
        response.setStatus(e.getCode());
        return e.getMsg();
    }

}
