package com.psych.controller;

import com.psych.common.Result;
import com.psych.entity.XinlingzhuanlanEntity;
import com.psych.service.XinlingzhuanlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 心灵专栏
 * 后端接口
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@RestController
@RequestMapping("/xinlingzhuanlan")
public class XinlingzhuanlanController {
    @Autowired
    private XinlingzhuanlanService xinlingzhuanlanService;
}
