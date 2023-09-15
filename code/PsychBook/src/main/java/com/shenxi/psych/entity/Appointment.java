package com.shenxi.psych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author shenxi
 * @Date 2023/10/12 16:45
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    private Integer id;
    private Integer stuId;
    private Integer doctorId;
    private String dates;
    private String times;
    private String content;
    private Integer state;
    private String cause;
    private Long gmtCreate;

    private Doctor doctor;
    private Student student;
}
