package com.shenxi.psych.mapper;

import com.shenxi.psych.entity.Appointment;
import com.shenxi.psych.entity.Patient;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/10/4 21:42
 * @Version 1.0
 */
public interface PatientMapper {

    @Insert("insert into patient(stu_number,name,gender,age,tel,email,password,state,gmt_create,gmt_modified) values (#{stuNumber}, #{name}, #{gender}, #{age},#{tel},#{email},#{password},#{state},#{gmtCreate},#{gmtModified})")
    void insertStu(Patient patient);

    @Select("select * from patient where id = #{id}")
    Patient selectPatientById(Integer id);

    @Select("select * from patient where stu_number = #{stuNumber} and password = #{password}")
    Patient selectPatient(String stuNumber, String password);

    @Update("update patient set state = #{state} where id = #{id}")
    Integer updatePatientState(Boolean state, Integer id);

    @Select("select * from patient where stu_number = #{stuNumber}")
    Patient selectStuByStuNumber(String stuNumber);

    @Select("select * from patient where tel = #{tel}")
    Patient selectStuByTel(String tel);

    @Select("select id from patient where stu_number = #{stuNumber}")
    Integer selectIdByStuNumber(String stuNumber);

    @Select("select distinct stu_id from ask_and_answer where quest_id = #{questionId}")
    Integer getStuIdByQuestionId(Integer questionId);

    //根据questionId查询咨询者
    @Results({
            @Result(column = "stu_id",property = "id"),
            @Result(column = "stu_id",property = "name",one = @One(select = "com.shenxi.psych.mapper.PatientMapper.getUsernameById",fetchType = FetchType.DEFAULT))
    })
    @Select("select distinct stu_id from ask_and_answer where quest_id = #{questionId}")
    Patient getStuByQuesId(Integer questionId);

    @Select("select name from patient where id = #{stu_id}")
    String getUsernameById(String stu_id);

    @Select("select * from patient where state = #{state}")
    List<Patient> getDoctorState(boolean state);

    @Select("select * from patient where id = #{id}")
    Patient getStuById(Integer id);

    @Select("select * from patient")
    List<Patient> getAllPatient();

    @Select("select * from patient order by state DESC")
    List<Patient> getStuPage();

    @Insert("insert into appointment(stu_id,doctor_id,dates,state,times,content,gmt_create) values (#{stuId},#{doctorId},#{dates},#{state},#{times},#{content},#{gmtCreate})")
    void insertAppointment(Appointment appointment);

    //根据questionId查询心理医生
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "doctor_id",property = "doctor",one = @One(select = "com.shenxi.psych.mapper.DoctorMapper.getDoctorNameById",fetchType = FetchType.DEFAULT))
    })
    @Select("select * from appointment where stu_id = #{stuId} order by gmt_create DESC")
    List<Appointment> getMyAppointment(Integer stuId);

    @Delete("delete from appointment where id = #{appointmentId}")
    void removeAppointmentByAppId(Integer appointmentId);

    @Delete("delete from patient where id = #{patientId}")
    void deletePatientById(Integer patientId);
}
