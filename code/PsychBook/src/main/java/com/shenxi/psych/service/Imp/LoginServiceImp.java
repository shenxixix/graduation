package com.shenxi.psych.service.Imp;

import com.shenxi.psych.entity.Login;
import com.shenxi.psych.entity.Patient;
import com.shenxi.psych.mapper.LoginMapper;
import com.shenxi.psych.service.LoginService;
import com.shenxi.psych.utils.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author shenxi
 * @Date 2023/10/4 17:31
 * @Version 1.0
 */
@Service
public class LoginServiceImp implements LoginService {

    private final static Logger logger = LoggerFactory.getLogger(LoginServiceImp.class);

    @Autowired
    LoginMapper loginMapper;

    @Override
    public String justLogin(Login login) {
        return loginMapper.justLogin(login);
    }

    @Override
    public Login getLoginFromStu(Patient patient) {
        Login login = new Login();
        login.setAccountId(patient.getId());
        login.setAccountName(patient.getName());
        login.setPassword(Md5Util.StringInMd5(patient.getPassword()));
        return login;
    }

    @Override
    public String lkUseridByUsername(String username) {
        return null;
    }
}
