package com.shenxi.psych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/10/4 23:14
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Integer id;

    private String content;

    private Integer viewCount;

    private Integer likes;

    private boolean anonymous;

    private boolean status;

    private Long gmtCreate;

    private Long gmtModified;

    private Student student;
    private List<AskAndAnswer> askAndAnsList;
    private List<QuestionAndTag> questionAndTags;

    private String createTime;
    private String updateTime;

}
