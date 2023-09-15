package com.shenxi.psych.service;

import com.shenxi.psych.entity.ChatFriends;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/10/10 14:37
 * @Version 1.0
 */
@Service
public interface ChatFriendsService {
    Object LkUserinfoByUserid(String uid);

    List<ChatFriends> findUserAllFriends(Integer userId);

    List<Integer> getFriendsId(Integer id);

    void setChatFriends(Integer userId, Integer doctorId);

    List<ChatFriends> findUserAllFriendsInStu(Integer id);
}
