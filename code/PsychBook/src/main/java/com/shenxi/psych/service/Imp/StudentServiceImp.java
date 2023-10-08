package com.shenxi.psych.service.Imp;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shenxi.psych.entity.Appointment;
import com.shenxi.psych.entity.Login;
import com.shenxi.psych.entity.Patient;
import com.shenxi.psych.entity.UserInfo;
import com.shenxi.psych.mapper.LoginMapper;
import com.shenxi.psych.mapper.PatientMapper;
import com.shenxi.psych.service.RedisService.UserRedisService;
import com.shenxi.psych.service.PatientService;
import com.shenxi.psych.utils.Md5Util;
import com.shenxi.psych.utils.exception.CMSException;
import com.shenxi.psych.utils.exception.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/10/4 21:33
 * @Version 1.0
 */
@Service
public class StudentServiceImp implements PatientService {

    private final static Logger logger = LoggerFactory.getLogger(StudentServiceImp.class);

    @Autowired
    PatientMapper patientMapper;

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    UserRedisService userRedisService;

    @Transactional
    @Override
    public int insertPatientAndLogin(Patient patient) {
        patientMapper.insertStu(patient);
        String stuNumber = patient.getStuNumber();
        Integer accountId = patientMapper.selectIdByStuNumber(stuNumber);
        if(accountId != null) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "该编号已经注册，请更换");
        }
        Patient pt = patientMapper.selectStuByTel(patient.getTel());
        if(pt != null){
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "该电话号码已经注册，请更换");
        }
        patientMapper.insertStu(patient);

        // TODO 新增用户关系表
        return 1;
    }

    /**
     *  用户名密码登录
     * @param stuNumber 用户名
     * @param password  密码
     * @return
     */
    @Override
    public Patient stuChecked(String stuNumber, String password) {
        Patient patient = patientMapper.selectStuByStuNumber(stuNumber);
        if(patient == null) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "用户不存在");
        }
        if(!patient.getPassword().equals(password)) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "用户密码错误");
        }
        logger.info("get patient is->{}", JSON.toJSON(patient));
        // TODO 新增登录信息表
        return patient;
    }

    /**
     * 验证码登录
     * @param tel
     * @param authcode
     * @return
     */
    @Override
    public Patient stuAuthcode(String tel, String authcode) {
        Patient patient = patientMapper.selectStuByTel(tel);
        if(patient == null) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "用户不存在");
        }
        if(patient == null || !authcode.equals(getCodeByTel(tel))) {
            throw new CMSException(ResultCodeEnum.PARAM_ERROR.getCode(), "验证码错误");
        }
        logger.info("get student is->{}", JSON.toJSON(patient));
        return patient;
    }

    @Override
    public Patient getStuById(Integer id) {
        return patientMapper.getStuById(id);
    }

    @Override
    public Patient getStuByStuNumber(String stuNumber) {
        Patient patient = patientMapper.selectStuByStuNumber(stuNumber);
        logger.info("select student by id is ->{}",JSON.toJSON(patient));
        return patient;
    }

    @Override
    public Boolean updatePatientState(Boolean state, Patient patient) {
        //先删除Redis中的在线状态
        try{
            userRedisService.removePatientByValue(patient);
        }catch (Exception e){
      //      throw new CMSException(ResultCodeEnum.UPDATE_STU_STATE_IN_REDIS);
        }

        Integer id = patient.getId();
        //再修改数据库中的状态为离线
        Integer res = patientMapper.updatePatientState(state,id);
     //   if (res == 0)
      //      throw new CMSException(ResultCodeEnum.UPDATE_STU_STATE_IN_DB);
      //  else
        return true;
    }

    @Override
    public Patient getStuByQuestionId(Integer questionId) {
        Integer stuId = patientMapper.getStuIdByQuestionId(questionId);
        Patient patient = patientMapper.selectPatientById(stuId);
        return patient;
    }

    @Override
    public PageInfo<Patient> getAllPatient(Integer pageNum, Integer pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Patient> pageInfo = getPageInfo(pageNum, pageSize);
        return pageInfo;
    }

    @Override
    public PageInfo<Patient> getPatientPage(Integer pageNum, Integer pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Patient> pageInfo = getPageInfoV2(pageNum, pageSize);
        return pageInfo;
    }

    @Override
    public void insertAppointment(Appointment appointment) {
        patientMapper.insertAppointment(appointment);
    }

    @Override
    public PageInfo<Appointment> getMyAppointment(Integer pageNum, Integer pageSize, Integer stuId) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Appointment> pageInfo = getPageInfoV3(pageNum, pageSize,stuId);
        return pageInfo;
    }

    @Override
    public void removeAppointmentByAppId(Integer appointmentId) {
        patientMapper.removeAppointmentByAppId(appointmentId);
    }

    @Override
    public void deleteStudentById(Integer studentId) {
        patientMapper.deletePatientById(studentId);
    }

    private PageInfo<Appointment> getPageInfoV3(Integer pageNum, Integer pageSize, Integer stuId) {
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
            List<Appointment> appointments = patientMapper.getMyAppointment(stuId);
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

    private PageInfo<Patient> getPageInfoV2(Integer pageNum, Integer pageSize) {
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
            List<Patient> patients = patientMapper.getStuPage();
            PageInfo<Patient> pageInfo = new PageInfo<>(patients, pageSize);
            pageInfo.setList(patients);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }

    private PageInfo<Patient> getPageInfo(Integer pageNum, Integer pageSize) {
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
            List<Patient> patients = patientMapper.getAllPatient();
            PageInfo<Patient> pageInfo = new PageInfo<>(patients, pageSize);
            pageInfo.setList(patients);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }

    private String getCodeByTel(String tel) {
        return "888888";
    }
}
