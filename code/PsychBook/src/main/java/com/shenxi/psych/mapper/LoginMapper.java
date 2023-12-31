package com.shenxi.psych.mapper;

import com.shenxi.psych.entity.Login;
import com.shenxi.psych.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @Author shenxi
 * @Date 2023/10/4 17:34
 * @Version 1.0
 */
public interface LoginMapper {
    //判断登录
    @Select("select account_id from login where account_name=#{accountName} and password=#{password}")
    String justLogin(Login login);

    @Insert("insert into login(account_id,account_name,password,gmt_create,gmt_modified) values (#{accountId},#{accountName},#{password},#{gmtCreate},#{gmtModified})")
    void insertLogin(Login login);

    @Insert("insert into user_info(nick_name,user_id,gmt_create,gmt_modified) values (#{nickName},#{userId},#{gmtCreate},#{gmtModified})")
    void insertUserInfo(UserInfo userinfo);
}
