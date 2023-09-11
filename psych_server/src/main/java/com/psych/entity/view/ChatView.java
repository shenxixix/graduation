package com.psych.entity.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.psych.entity.ChatEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
 

/**
 * 聊天
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@TableName("chat")
public class ChatView  extends ChatEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public ChatView(){
	}
 
 	public ChatView(ChatEntity chatEntity){
 	try {
			BeanUtils.copyProperties(this, chatEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
