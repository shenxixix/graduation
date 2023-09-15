package com.shenxi.psych.service;

import com.github.pagehelper.PageInfo;
import com.shenxi.psych.entity.Appointment;
import com.shenxi.psych.entity.Student;

/**
 * @Author shenxi
 * @Date 2023/10/4 21:12
 * @Version 1.0
 */
public interface StudentService {

    int insertStudentAndLogin(Student student);

    Student stuChecked(String stuNumber, String password);

    Student getStuById(Integer id);

    Student getStuByStuNumber(String stuNumber);

    Boolean updateStudentState(Boolean state,Student student);

    Student getStuByQuestionId(Integer questionId);

    PageInfo<Student> getAllStudent(Integer pageNum, Integer pageSize);

    PageInfo<Student> getStuPage(Integer pageNum, Integer pageSize);

    void insertAppointment(Appointment appointment);

    PageInfo<Appointment> getMyAppointment(Integer pageNum, Integer pageSize, Integer id);

    void removeAppointmentByAppId(Integer appointmentId);

    void deleteStudentById(Integer studentId);
}
