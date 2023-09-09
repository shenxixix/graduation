package com.psych.domain.auto.user.mapper;

import com.psych.domain.auto.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mk
 * @since 2023-09-09
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> findUsers();

    @Select("select * from user")
    List<User> findUsersTwo();
}
