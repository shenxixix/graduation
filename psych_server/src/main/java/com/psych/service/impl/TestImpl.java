package com.psych.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psych.domain.auto.user.entity.User;
import com.psych.domain.auto.user.mapper.UserDao;
import com.psych.domain.auto.user.mapper.UserMapper;
import com.psych.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TestImpl implements TestService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findUsers() {
        List<User> users = userMapper.findUsersTwo();
        List<User> users2 =  userDao.findUsers();
        List<User> users3 = userMapper.selectList(new QueryWrapper<User>());
        return userMapper.findUsers();
    }
}
