package com.shenxi.psych;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shenxi.psych.mapper")
public class PsychApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsychApplication.class, args);
	}

}
