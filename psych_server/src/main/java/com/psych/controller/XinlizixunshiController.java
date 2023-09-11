package com.psych.controller;

import com.psych.service.TokenService;
import com.psych.service.XinlizixunshiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 心理咨询师
 * 后端接口
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@RestController
@RequestMapping("/xinlizixunshi")
public class XinlizixunshiController {
    @Autowired
    private XinlizixunshiService xinlizixunshiService;
    
	@Autowired
	private TokenService tokenService;

}
