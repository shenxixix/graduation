package com.shenxi.psych.service;

import com.github.pagehelper.PageInfo;
import com.shenxi.psych.entity.Admin;
import org.springframework.stereotype.Service;

/**
 * @Author shenxi
 * @Date 2023/10/29 16:40
 * @Version 1.0
 */
@Service
public interface AdminService {

    Admin checkAdmin(Admin admin);

    Admin getAdminByName(String name);

    PageInfo<Admin> getAllAdmin(Integer pageNum, Integer pageSize);

    void deleteAdminById(Integer adminId);
}
