package com.shenxi.psych.mapper;

import com.shenxi.psych.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/9/29 16:39
 * @Version 1.0
 */
public interface AdminMapper {

    @Select("select * from admin where name = #{name} and password = #{password}")
    Admin checkAdmin(Admin admin);

    @Select("select * from admin where name = #{name}")
    Admin getAdminByName(String name);

    @Select("select * from admin")
    List<Admin> getAllAdmin();

    @Delete("delete from admin where id = #{adminId}")
    void deleteAdminById(Integer adminId);
}
