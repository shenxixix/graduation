package com.yolo.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:shenxi
 * @Des:
 * @Date:2019/2/27
 */
@Configuration
@Slf4j
public class FeignInterceptor {
    @Bean
    public RequestInterceptor currentUserRequestInterceptor() {
        return (RequestTemplate template) -> {
            /**
             * 主要完成指定feign需要在header中增加鉴权加密签名相关数据
             */
            // 暂未实现
            log.info("-----------拦截feign----------------");
//            ObjectMapper objectMapper = new ObjectMapper();
//            String str = new String(template.body());
//            System.out.println(str);
//            template.header("simpleUser", JsonUtil.toJson(userVo));
        };
    }
}
