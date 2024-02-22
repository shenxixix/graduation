package com.yolo.hw;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetAddress;
import java.net.UnknownHostException;


@Log4j2
@EnableEurekaClient
// 这里到时修改下包
@EnableFeignClients(basePackages = {"com.zoom", "com.yolo"})
@SpringBootApplication(scanBasePackages = {
        "com.yolo.hw.domain",
        "com.yolo.hw.opt",
        "com.zoom.common.threadpool",
        "com.yolo.config.swagger",
        "com.yolo.config.weblog",
        "com.yolo.config.normal",
        "com.yolo.config.feign",
        "com.yolo.config.resttemplate",
        "com.yolo.config.redis"
})
public class HwOpenManagerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HwOpenManagerApplication.class, args);
        log.info("华潍租赁管理平台服务完成");
        log.info("**************************************");
        try {
            String port = context.getEnvironment().getProperty("server.port");
            String path = context.getEnvironment().getProperty("server.servlet.context-path");
            InetAddress[] addresses = InetAddress.getAllByName(InetAddress.getLocalHost().getHostAddress());
            for (InetAddress ip : addresses) {
                String host = ip.getHostAddress();
                log.info("华潍租赁管理平台服务信息:" + host + ":" + port + path);
            }
        } catch (UnknownHostException e) {
            log.error("华潍租赁管理平台服务:", e);
        }

        log.info("**************************************");
        System.out.println("============华潍租赁管理平台服务启动完成=============");
    }
}
