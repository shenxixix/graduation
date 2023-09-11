package com.psych.entity.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.psych.entity.DiscussxinlizixunshiEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
 

/**
 * 心理咨询师评论表
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@TableName("discussxinlizixunshi")
public class DiscussxinlizixunshiView  extends DiscussxinlizixunshiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public DiscussxinlizixunshiView(){
	}
 
 	public DiscussxinlizixunshiView(DiscussxinlizixunshiEntity discussxinlizixunshiEntity){
 	try {
			BeanUtils.copyProperties(this, discussxinlizixunshiEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
