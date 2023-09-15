package com.shenxi.psych.service;

import com.shenxi.psych.entity.Login;
import com.shenxi.psych.entity.Patient;
import org.springframework.stereotype.Service;

/**
 * @Author shenxi
 * @Date 2023/10/4 17:25
 * @Version 1.0
 */
@Service
public interface LoginService {

    String justLogin(Login login);

    Login getLoginFromStu(Patient patient);

    String lkUseridByUsername(String username);

}
