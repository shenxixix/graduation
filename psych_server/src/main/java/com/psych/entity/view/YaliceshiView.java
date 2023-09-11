package com.psych.entity.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.psych.entity.YaliceshiEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
 

/**
 * 压力测试
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@TableName("yaliceshi")
public class YaliceshiView  extends YaliceshiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public YaliceshiView(){
	}
 
 	public YaliceshiView(YaliceshiEntity yaliceshiEntity){
 	try {
			BeanUtils.copyProperties(this, yaliceshiEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
