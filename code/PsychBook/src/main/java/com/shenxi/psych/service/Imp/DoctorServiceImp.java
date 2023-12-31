package com.shenxi.psych.service.Imp;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shenxi.psych.entity.Appointment;
import com.shenxi.psych.entity.Doctor;
import com.shenxi.psych.entity.Login;
import com.shenxi.psych.entity.Patient;
import com.shenxi.psych.mapper.DoctorMapper;
import com.shenxi.psych.mapper.LoginMapper;
import com.shenxi.psych.mapper.PatientMapper;
import com.shenxi.psych.service.DoctorService;
import com.shenxi.psych.service.RedisService.UserRedisService;
import com.shenxi.psych.utils.exception.CMSException;
import com.shenxi.psych.utils.exception.ResultCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/10/6 20:09
 * @Version 1.0
 */
@Service
public class DoctorServiceImp implements DoctorService {

    private final static Logger logger = LoggerFactory.getLogger(DoctorServiceImp.class);

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private UserRedisService userRedisService;

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Doctor getDoctorById(Integer id) {
        Doctor doctor = doctorMapper.getDoctorById(id);
        logger.info("doctor is ->{}", JSON.toJSON(doctor));
        return doctor;
    }

    @Override
    public List<Doctor> getDoctorState(Boolean state) {
        List<Doctor> doctorList = doctorMapper.getDoctorState(state);
        logger.info("在线的心理医生列表->{}",JSON.toJSON(doctorList));
        return doctorList;
    }

    @Override
    public Doctor getStuByStuNumber(String doctorNumber) {
        Doctor doctor = doctorMapper.getDoctorByDoctorNumber(doctorNumber);
        logger.info("根据doctorNumber从db中查出的doctor->{}",JSON.toJSON(doctor));
        return doctor;
    }

    @Override
    public void insertDoctor(Doctor doctor) {
        String username = doctor.getDoctorNumber();
        String password = doctor.getPassword();
        String tel = doctor.getTel();
        if(StringUtils.isEmpty(username)) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "用户名不能为空");
        }
        if(StringUtils.isEmpty(password)) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(),"密码不能为空");
        }
        if(StringUtils.isEmpty(tel)) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(),"电话号码不能为空");
        }
        if(doctorMapper.getDoctorByDoctorNumber(username) != null) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(),"该用户名已经存在");
        }
        if(doctorMapper.selectDoctorByTel(tel) != null) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(),"该手机号已经存在");
        }
        doctorMapper.insertDoctor(doctor);
    }

    @Override
    public List<Patient> getStuState(boolean state) {
        return patientMapper.getDoctorState(state);
    }

    /**
     *  用户名密码登录
     * @param doctorNumber 用户名
     * @param password 密码
     * @return
     */
    @Override
    public Doctor doctorChecked(String doctorNumber, String password) {
        Doctor doctor =doctorMapper.getDoctorByDoctorNumber(doctorNumber);
        if(doctor == null) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(),"用户不存在");
        }
        if(doctor == null || !doctor.getPassword().equals(password)) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(),"密码错误");
        }
        logger.info("get doctor is->{}", JSON.toJSON(doctor));
        // 记录登录日志
        Login login = new Login();
        login.setAccountId(doctor.getId());
        login.setAccountName(doctor.getDoctorNumber());
        login.setGmtCreate(new Date().getTime());
        loginMapper.insertLogin(login);
        return doctor;
    }

    /**
     * 验证码登录
     * @param tel 电话号码
     * @param authcode 验证码
     * @return
     */
    @Override
    public Doctor doctorAuthcode(String tel, String authcode) {
        Doctor doctor = doctorMapper.selectDoctorByTel(tel);
        if(doctor == null) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "用户不存在");
        }
        if(doctor == null || !authcode.equals(getCodeByTel(tel))) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "验证码错误");
        }
        logger.info("get doctor is->{}", JSON.toJSON(doctor));
        Login login = new Login();
        login.setAccountId(doctor.getId());
        login.setAccountName(doctor.getDoctorNumber());
        login.setGmtCreate(new Date().getTime());
        loginMapper.insertLogin(login);
        return doctor;
    }

    @Override
    public Boolean updateDoctorState(boolean state, Doctor doctor) {
        //先删除Redis中的在线状态
        try{
            userRedisService.removeDoctorByValue(doctor);
        }catch (Exception e){
       //     throw new CMSException(ResultCodeEnum.UPDATE_DOCTOR_STATE_IN_REDIS);
        }

        Integer id = doctor.getId();
        //再修改数据库中的状态
        Integer res = doctorMapper.updateDoctorState(state,id);
        if (res == 0)
            throw new CMSException(ResultCodeEnum.UPDATE_DOCTOR_STATE_IN_DB);
        else
            return true;
    }


    @Override
    public PageInfo<Doctor> getAllDoctor(Integer pageNum, Integer pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
    // 获取
    PageInfo<Doctor> pageInfo = getPageInfo(pageNum, pageSize);
        return pageInfo;
}

    @Override
    public PageInfo<Doctor> getDoctorPage(Integer pageNum, Integer pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Doctor> pageInfo = getPageInfoV2(pageNum, pageSize);
        return pageInfo;

    }

    @Override
    public PageInfo<Appointment> getMyAppointment(Integer pageNum, Integer pageSize, Integer doctorId) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Appointment> pageInfo = getPageInfoV3(pageNum, pageSize,doctorId);
        return pageInfo;
    }

    @Override
    public void insertAppointment(String cause,Integer state, Integer appointmentId) {
        doctorMapper.insertAppointment(cause,state,appointmentId);
    }

    @Override
    public void delectDoctorById(Integer doctorId) {
        doctorMapper.delectDoctorById(doctorId);
    }

    private PageInfo<Appointment> getPageInfoV3(Integer pageNum, Integer pageSize, Integer doctorId) {
        //判断非空
        if (pageNum == null) {
            pageNum = 1; //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5; //设置默认每页显示的数据数
        }
        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Appointment> appointments = doctorMapper.getMyAppointment(doctorId);
            PageInfo<Appointment> pageInfo = new PageInfo<>(appointments, pageSize);
            pageInfo.setList(appointments);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }

    private PageInfo<Doctor> getPageInfoV2(Integer pageNum, Integer pageSize) {
        //判断非空
        if (pageNum == null) {
            pageNum = 1; //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5; //设置默认每页显示的数据数
        }
        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Doctor> doctors = doctorMapper.getDoctorPage();

            PageInfo<Doctor> pageInfo = new PageInfo<>(doctors, pageSize);
            pageInfo.setList(doctors);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }

    private PageInfo<Doctor> getPageInfo(Integer pageNum, Integer pageSize) {
        //判断非空
        if (pageNum == null) {
            pageNum = 1; //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5; //设置默认每页显示的数据数
        }
        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Doctor> doctors = doctorMapper.getAllDoctor();
            PageInfo<Doctor> pageInfo = new PageInfo<>(doctors, pageSize);
            pageInfo.setList(doctors);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }

    /**
     *得到数据库中所有Doctor Id
     * @return
     */
    private List<Integer> getDoctorIds() {
        List<Integer> ids = doctorMapper.getAll();
        logger.info("所有id->{}",JSON.toJSON(ids));
        return ids;
    }

    private String getCodeByTel(String tel) {
        return "888888";
    }
}
