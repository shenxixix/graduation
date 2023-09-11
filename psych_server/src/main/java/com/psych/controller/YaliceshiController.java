package com.psych.controller;

import com.psych.service.YaliceshiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 压力测试
 * 后端接口
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@RestController
@RequestMapping("/yaliceshi")
public class YaliceshiController {

    @Autowired
    private YaliceshiService yaliceshiService;

}
