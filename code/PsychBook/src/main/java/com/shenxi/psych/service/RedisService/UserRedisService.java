package com.shenxi.psych.service.RedisService;

import com.alibaba.fastjson.JSON;
import com.shenxi.psych.entity.Doctor;
import com.shenxi.psych.entity.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author shenxi
 * @Date 2023/10/4 22:38
 * @Version 1.0
 */
@Service
public class UserRedisService {
    private final static Logger logger = LoggerFactory.getLogger(UserRedisService.class);

    @Autowired
    private StringRedisTemplate redisUserTemplate; //聊天信息进redis缓存

    @Autowired
    private RedisTemplate<String,Patient> patientRedisTemplate;//将Student在线信息存入redis

    @Autowired
    private RedisTemplate<String,Doctor> doctorRedisTemplate;//将Doctor在线信息存入redis


    //格式：user:stu,Student student
    public void insertStu(Patient student){
        patientRedisTemplate.opsForList().leftPush("user:patient",student);
        patientRedisTemplate.expire("user:patient",3, TimeUnit.HOURS);
    }

    //格式：user:doctor,Doctor doctor
    public void insertDoctor(Doctor doctor){
        doctorRedisTemplate.opsForList().leftPush("user:doctor",doctor);
        doctorRedisTemplate.expire("user:doctor",3, TimeUnit.HOURS);
    }

    public void removePatientByValue(Patient student) {
        patientRedisTemplate.opsForList().remove("user:patient",0,student);
        List<Patient> students = patientRedisTemplate.opsForList().range("user:patient", 0, -1);
        logger.info("删除Redis中在线状态后的list->{}",JSON.toJSON(students));
    }

    //检查该咨询者是否在线
    public Boolean isStuExist(Patient student){
        logger.info("student is->{}",JSON.toJSON(student));
        List<Patient> students= patientRedisTemplate.opsForList().range("user:patient", 0, -1);
        assert students != null : "stu_ids is null";
        return students.contains(student);
    }

    //检查该心理医生是否在线
    public Boolean isDoctorExist(Doctor doctor){
        logger.info("Redis中检查是否在线doctor is->{}",JSON.toJSON(doctor));
        List<Doctor> doctors= doctorRedisTemplate.opsForList().range("user:doctor", 0, -1);
        assert doctors != null : "doctors is null";
        return doctors.contains(doctor);
    }

    //得到在线心理医生
    public List<Doctor> getDoctorsOnline() {
        List<Doctor> doctors = doctorRedisTemplate.opsForList().range("user:doctor", 0, 1);
        logger.info("Redis中在线心理医生列表->{}",JSON.toJSON(doctors));
        return doctors;
    }

    public List<Patient> getPatientsOnline() {
        List<Patient> students = patientRedisTemplate.opsForList().range("user:patient", 0, 1);
        logger.info("所有在线咨询者列表->{}",JSON.toJSON(students));
        return students;
    }

    public void removeDoctorByValue(Doctor doctor) {
        doctorRedisTemplate.opsForList().remove("user:doctor",0,doctor);
    }
}
