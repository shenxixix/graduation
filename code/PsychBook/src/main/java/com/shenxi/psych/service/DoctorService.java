package com.shenxi.psych.service;

import com.github.pagehelper.PageInfo;
import com.shenxi.psych.entity.Appointment;
import com.shenxi.psych.entity.Doctor;
import com.shenxi.psych.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/10/6 20:11
 * @Version 1.0
 */
@Service
public interface DoctorService {
    Doctor getDoctorById(Integer id);

    List<Doctor> getDoctorState(Boolean state);

    Doctor getStuByStuNumber(String doctorNumber);

    void insertDoctor(Doctor doctor);

    List<Student> getStuState(boolean state);

    Doctor doctorChecked(String doctorNumber,String password);

    Boolean updateDoctorState(boolean state, Doctor doctor);

    PageInfo<Doctor> getAllDoctor(Integer pageNum, Integer pageSize);

    PageInfo<Doctor> getDoctorPage(Integer pageNum, Integer pageSize);

    PageInfo<Appointment> getMyAppointment(Integer pageNum, Integer pageSize, Integer doctorId);

    void insertAppointment(String cause,Integer state, Integer appointmentId);

    void delectDoctorById(Integer doctorId);
}
