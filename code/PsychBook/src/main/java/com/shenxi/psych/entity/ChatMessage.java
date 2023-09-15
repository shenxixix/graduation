package com.shenxi.psych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author shenxi
 * @Date 2023/10/24 22:10
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ChatMessage {
    private Integer sendUserId;

    private Integer receiveUserId;

    private Date sendTime;

    private String massageType;

    private String sendText;

    private String sendTimeToString;
}
