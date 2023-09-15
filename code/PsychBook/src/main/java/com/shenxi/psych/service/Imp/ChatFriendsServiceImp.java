package com.shenxi.psych.service.Imp;

import com.shenxi.psych.entity.ChatFriends;
import com.shenxi.psych.entity.Doctor;
import com.shenxi.psych.mapper.ChatFriendsMapper;
import com.shenxi.psych.mapper.ChatMessageMapper;
import com.shenxi.psych.service.ChatFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/10/10 14:38
 * @Version 1.0
 */
@Service
public class ChatFriendsServiceImp implements ChatFriendsService {

    @Autowired
    ChatFriendsMapper chatFriendsMapper;

    @Autowired
    ChatMessageMapper chatMessageMapper;

    @Override
    public Object LkUserinfoByUserid(String uid) {
        return null;
    }

    @Override
    public List<ChatFriends> findUserAllFriends(Integer userId) {
        return chatFriendsMapper.findUserAllFriends(userId);
    }

    @Override
    public List<Integer> getFriendsId(Integer id) {
        return chatFriendsMapper.getFriendsId(id);
    }

    @Override
    public void setChatFriends(Integer userId, Integer doctorId) {
        //建立咨询者和老师的关系
        ChatFriends chatFriends = new ChatFriends();
        chatFriends.setUserId(userId);
        chatFriends.setFriendId(doctorId);
        chatFriendsMapper.InsertUserFriend(chatFriends);

        //建立老师和咨询者的关系
        chatFriends.setUserId(doctorId);
        chatFriends.setFriendId(userId);
        chatFriendsMapper.InsertUserFriend(chatFriends);
    }

    @Override
    public List<ChatFriends> findUserAllFriendsInStu(Integer id) {
        List<ChatFriends> chatFriends = new ArrayList<>();
        List<Doctor> friends = chatFriendsMapper.findUserAllFriendsInStu(id);
        for (Doctor friend: friends){
            ChatFriends chatFriend = new ChatFriends();
            chatFriend.setUserId(friend.getId());
            chatFriend.setNickName(friend.getName());
            chatFriends.add(chatFriend);
        }
        return chatFriends;
    }
}
