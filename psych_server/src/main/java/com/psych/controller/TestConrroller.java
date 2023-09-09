package com.psych.controller;

import com.psych.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(value = "测试", tags = "测试")
@RestController
@Log4j2
public class TestConrroller {

    @Autowired
    private TestService testService;

    @ApiOperation(value = "广告列表", notes = "广告列表", response = Object.class)
    @RequestMapping(value = "/test/test", method = RequestMethod.GET)
    public Object queryAddList( HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        return testService.findUsers();
    }
}
