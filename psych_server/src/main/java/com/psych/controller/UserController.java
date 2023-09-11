
package com.psych.controller;


import com.psych.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录相关
 */
@RequestMapping("users")
@RestController
public class UserController{

	@Autowired
	private UserService userService;

}
