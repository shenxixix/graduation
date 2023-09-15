package com.shenxi.psych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author shenxi
 * @Date 2023/10/4 17:09
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    private Integer id;
    private String doctorNumber;
    private String name;
    private String password;
    private Integer gender;
    private Integer age;
    private Integer teachYears;
    private String graduatedSchool;
    private String tel;
    private String email;
    private Boolean state;
    private Long gmtCreate;
    private Long gmtModified;

}
