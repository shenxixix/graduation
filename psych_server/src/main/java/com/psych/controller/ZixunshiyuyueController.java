package com.psych.controller;

import com.psych.service.ZixunshiyuyueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 咨询师预约
 * 后端接口
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@RestController
@RequestMapping("/zixunshiyuyue")
public class ZixunshiyuyueController {
    @Autowired
    private ZixunshiyuyueService zixunshiyuyueService;

}
