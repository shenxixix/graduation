
package com.psych.controller;


import com.psych.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录相关
 */
@RequestMapping("config")
@RestController
public class ConfigController{
	
	@Autowired
	private ConfigService configService;

}
