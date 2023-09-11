package com.psych.entity.view;

import com.baomidou.mybatisplus.annotation.TableName;
import com.psych.entity.XinlingzhuanlanEntity;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
 

/**
 * 心灵专栏
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2023-09-11 21:46:13
 */
@TableName("xinlingzhuanlan")
public class XinlingzhuanlanView  extends XinlingzhuanlanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public XinlingzhuanlanView(){
	}
 
 	public XinlingzhuanlanView(XinlingzhuanlanEntity xinlingzhuanlanEntity){
 	try {
			BeanUtils.copyProperties(this, xinlingzhuanlanEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
