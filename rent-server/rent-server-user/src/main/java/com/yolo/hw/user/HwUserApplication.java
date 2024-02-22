package com.yolo.hw.user;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.zoom", "com.yolo"})
@SpringBootApplication
@Log4j2
@ComponentScan({
		"com.yolo.hw.user",
		"com.yolo.hw.domain",
		"com.yolo.config.swagger",
		"com.yolo.config.weblog",
		"com.yolo.config.normal",
		"com.yolo.config.feign",
		"com.yolo.config.redis",
		"com.yolo.config.resttemplate"
})
public class HwUserApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(HwUserApplication.class, args);
		log.info("华为租赁用户服务启动完成");
		log.info("**************************************");
//		String appName = "";
		try {
//			appName = context.getEnvironment().getProperty("app_name");
			String port = context.getEnvironment().getProperty("server.port");
			String path = context.getEnvironment().getProperty("server.servlet.context-path");
			InetAddress[] addresses = InetAddress.getAllByName(InetAddress.getLocalHost().getHostAddress());
			for (InetAddress ip : addresses) {
				String host = ip.getHostAddress();
				log.info("车队用户服务信息:" + host + ":" + port + path);
			}
		} catch (UnknownHostException e) {
			log.error("华为租赁服务:", e);
		}
		log.info("**************************************");
		System.out.println("============华为租赁服务启动完成=============");
	}
}
