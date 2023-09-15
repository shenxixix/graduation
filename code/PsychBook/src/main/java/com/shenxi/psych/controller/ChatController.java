package com.shenxi.psych.controller;

import com.alibaba.fastjson.JSON;
import com.shenxi.psych.entity.ChatFriends;
import com.shenxi.psych.entity.ChatMessage;
import com.shenxi.psych.entity.Doctor;
import com.shenxi.psych.entity.Patient;
import com.shenxi.psych.service.ChatFriendsService;
import com.shenxi.psych.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author shenxi
 * @Date 2023/10/24 18:13
 * @Version 1.0
 */
@Controller
public class ChatController {
    private final static Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    ChatFriendsService chatFriendsService;

    @Autowired
    ChatMessageService chatMessageService;

    /**
     * 跳转到聊天
     * */
    @GetMapping("/chat/ct")
    public String toChat(){
        return "/chat/chats";
    }

    /**
     *查询用户的好友
     * @param session
     * @return
     */
    @PostMapping("/chat/findUserFriends")
    @ResponseBody
    public List<ChatFriends> findUserFriends(HttpSession session){
        Doctor doctor = (Doctor) session.getAttribute("doctor");
        Patient patient = (Patient) session.getAttribute("patient");
        if (doctor != null){
            List<ChatFriends> allFriends = chatFriendsService.findUserAllFriends(doctor.getId());
            logger.info("doctor端聊天页面查找的所有好友->{}", JSON.toJSON(allFriends));
            return allFriends;
        }else {
            List<ChatFriends> allFriends = chatFriendsService.findUserAllFriendsInStu(patient.getId());
            logger.info("student端聊天页面查找的所有好友->{}", JSON.toJSON(allFriends));
            return allFriends;
        }


    }

    /***
     * 查询两个用户之间的聊天记录
     * */
    @PostMapping("/chat/findUserChatMsg/{receiveUserId}")
    @ResponseBody
    public List<ChatMessage> findFriendsChatMsg(HttpSession session, @PathVariable("receiveUserId")String receiveUserId){
        Integer userId=(Integer)session.getAttribute("userid");
        return chatMessageService.findTwoUserMsg(new ChatMessage().setSendUserId(userId).setReceiveUserId(Integer.valueOf(receiveUserId)));
    }
}
