package com.shenxi.psych.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author shenxi
 * @Date 2023/10/24 18:05
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatFriends {

    private Integer id;

    private Integer userId;

    private Integer friendId;

    private String nickName;

    private String userImg;

    private Long gmtCreate;
    private Long gmtModified;
}
