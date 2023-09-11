package com.psych.entity.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.psych.entity.XiaozhitiaoEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
 

/**
 * 小纸条
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@TableName("xiaozhitiao")
public class XiaozhitiaoView  extends XiaozhitiaoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public XiaozhitiaoView(){
	}
 
 	public XiaozhitiaoView(XiaozhitiaoEntity xiaozhitiaoEntity){
 	try {
			BeanUtils.copyProperties(this, xiaozhitiaoEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
