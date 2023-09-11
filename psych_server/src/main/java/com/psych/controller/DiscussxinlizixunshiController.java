package com.psych.controller;

import com.psych.service.DiscussxinlizixunshiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 心理咨询师评论表
 * 后端接口
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@RestController
@RequestMapping("/discussxinlizixunshi")
public class DiscussxinlizixunshiController {
    @Autowired
    private DiscussxinlizixunshiService discussxinlizixunshiService;

}
