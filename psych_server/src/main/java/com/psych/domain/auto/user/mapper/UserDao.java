package com.psych.domain.auto.user.mapper;

import com.psych.domain.auto.user.entity.User;

import java.util.List;

public interface UserDao {

    List<User> findUsers();
}
