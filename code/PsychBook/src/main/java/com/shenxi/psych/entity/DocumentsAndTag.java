package com.shenxi.psych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author shenxi
 * @Date 2023/10/29 23:05
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentsAndTag {
    private int id;
    private Integer documentId;
    private Tag tag;
}
