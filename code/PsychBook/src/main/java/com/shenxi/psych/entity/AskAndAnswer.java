package com.shenxi.psych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author shenxi
 * @Date 2023/10/18 14:33
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AskAndAnswer {
    private Integer id;
    private Integer questId;
    private Doctor doctor;
    private Student student;
    private String answer;
    private Long gmtCreate;

    private Long gmtModified;

    private String createTime;
    private String updateTime;
}
