package com.psych;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(scanBasePackages = {"com.psych"})
public class PsychApplication {

    private final static Logger logger = LoggerFactory.getLogger(PsychApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PsychApplication.class, args);
        logger.info("心理预约服务启动完成");
        logger.info("**************************************");
        try {
            String port = context.getEnvironment().getProperty("server.port");
            String path = context.getEnvironment().getProperty("server.context-path");
            InetAddress[] addresses = InetAddress.getAllByName(InetAddress.getLocalHost().getHostAddress());
            for (InetAddress ip : addresses) {
                String host = ip.getHostAddress();
                logger.info("心理预约服务启动完成信息:http://" + host + ":" + port + path);
                logger.info("心理预约服务接口文档api:http://" + host + ":" + port + path + "/swagger-ui.html");
            }
        } catch (UnknownHostException e) {
            logger.error("心理预约服务启动完成服务:", e);
        }
        logger.info("**************************************");
    }

}
