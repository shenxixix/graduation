package com.shenxi.psych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author shenxi
 * @Date 2023/10/18 14:35
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAndTag {
    private int id;
    private Integer questId;
    private Tag tag;
}
