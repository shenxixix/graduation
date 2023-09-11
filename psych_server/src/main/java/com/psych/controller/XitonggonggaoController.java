package com.psych.controller;

import com.psych.service.XitonggonggaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统公告
 * 后端接口
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@RestController
@RequestMapping("/xitonggonggao")
public class XitonggonggaoController {

    @Autowired
    private XitonggonggaoService xitonggonggaoService;

}
