package com.shenxi.psych.mapper;

import com.shenxi.psych.entity.Appointment;
import com.shenxi.psych.entity.Doctor;
import com.shenxi.psych.entity.Patient;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/10/6 20:15
 * @Version 1.0
 */
public interface DoctorMapper {

    //根据id查询
    @Select("select * from doctor where id = #{id}")
    Doctor getDoctorById(Integer id);

    //获取所有id数据
    @Select("select id from doctor")
    List<Integer> getAll();

    @Select("select * from doctor where state = #{state}")
    List<Doctor> getDoctorState(Boolean state);

    //查出id + username
    @Select("select id,name from doctor where id = #{id}")
    Doctor getDoctorNameById(Integer id);

    @Select("select * from doctor where doctor_number = #{doctorNumber}")
    Doctor getDoctorByDoctorNumber(String doctorNumber);

    @Insert("insert into doctor(doctor_number,name,gender,age,teach_years,graduated_school,tel,email,password," +
            "gmt_create) values (#{doctorNumber},#{name}, #{gender}, #{age},#{teachYears}, #{graduatedSchool}, #{tel}," +
            "#{email},#{password},#{gmtCreate})")
    void insertDoctor(Doctor doctor);

    @Select("select * from doctor where doctor_number = #{doctorNumber} and password = #{password}")
    Doctor getDoctorByDoctorNumberAndPassword(String doctorNumber, String password);

    @Update("update doctor set state = #{state} where id = #{id}")
    Integer updateDoctorState(boolean state, Integer id);

    @Select("select * from doctor")
    List<Doctor> getAllDoctor();

    @Select("select * from doctor order by state DESC")
    List<Doctor> getDoctorPage();

    //根据questionId查询心理医生
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "stu_id",property = "patient",one = @One(select = "com.shenxi.psych.mapper.PatientMapper.selectPatientById",fetchType = FetchType.DEFAULT))
    })
    @Select("select * from appointment where doctor_id = #{doctorId} order by gmt_create DESC")
    List<Appointment> getMyAppointment(Integer doctorId);

    @Update("UPDATE appointment SET cause = #{cause},state = #{state} WHERE id = #{appointmentId}")
    void insertAppointment(String cause,Integer state, Integer appointmentId);

    @Delete("delete from doctor where id = #{doctorId}")
    void delectDoctorById(Integer doctorId);

    @Select("select * from doctor where tel = #{tel}")
    Doctor selectDoctorByTel(String tel);
}
