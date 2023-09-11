package com.psych.controller;

import com.psych.service.XiaozhitiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 小纸条
 * 后端接口
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@RestController
@RequestMapping("/xiaozhitiao")
public class XiaozhitiaoController {
    @Autowired
    private XiaozhitiaoService xiaozhitiaoService;

}
