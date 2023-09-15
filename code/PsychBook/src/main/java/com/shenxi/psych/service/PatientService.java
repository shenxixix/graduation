package com.shenxi.psych.service;

import com.github.pagehelper.PageInfo;
import com.shenxi.psych.entity.Appointment;
import com.shenxi.psych.entity.Patient;

/**
 * @Author shenxi
 * @Date 2023/10/4 21:12
 * @Version 1.0
 */
public interface PatientService {

    int insertPatientAndLogin(Patient student);

    Patient stuChecked(String stuNumber, String password);

    Patient getStuById(Integer id);

    Patient getStuByStuNumber(String stuNumber);

    Boolean updatePatientState(Boolean state, Patient student);

    Patient getStuByQuestionId(Integer questionId);

    PageInfo<Patient> getAllPatient(Integer pageNum, Integer pageSize);

    PageInfo<Patient> getPatientPage(Integer pageNum, Integer pageSize);

    void insertAppointment(Appointment appointment);

    PageInfo<Appointment> getMyAppointment(Integer pageNum, Integer pageSize, Integer id);

    void removeAppointmentByAppId(Integer appointmentId);

    void deleteStudentById(Integer studentId);
}
