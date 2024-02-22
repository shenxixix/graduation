package com.yolo.config.feign;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolo.exception.ErrorEnum;
import com.yolo.exception.SysAppException;
import feign.Response;
import feign.Util;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

/**
 * @Author:shenxi
 * @Des:用于feign异常处理，转换统一的服务端异常处理
 * @Date:2017/11/7
 */
@Slf4j
@Configuration
public class FeignErrorDecoder implements feign.codec.ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        String body ="";
        try {
            String url= response.request().url();
            log.warn("feign调用失败url={};status={}",url,status);
            if (status>=400&&status<500){
                log.warn("feign调用失败url={};status={},客户端调用异常",url,status);
                return new SysAppException(ErrorEnum.NET_ERR);
            }
            if (status>=500&&status<600){
                log.warn("feign调用失败url={};status={},服务端返回异常",url,status);
                return new SysAppException(ErrorEnum.BUSINESS_ERR);
            }
            //int status = response.status();
            body = Util.toString(response.body().asReader());
            ObjectMapper mapper = new ObjectMapper(); //转换器
            Map<String,String>map=mapper.readValue(body,Map.class);
            SysAppException sysAppException = new SysAppException(status, map.get("msg"));
            return sysAppException;
        } catch (JsonMappingException e){
            //兼容返回不是json的，文本中有引号的全部替换掉
            return new SysAppException(status,body.replace("\"",""));
        }
        catch (IOException e) {
            return new SysAppException(ErrorEnum.NET_ERR);
        }
    }
}
