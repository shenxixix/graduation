package com.yolo.validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Author:shenxi
 * @Des:验证框架
 * @Date:2017/11/6
 */
public class StValiDator {
    public static Map<String, Object> validate(Object val) {
        if (val == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("msg", "请输入参数");
            return map;
        }
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<Object>> s = validator.validate(val);
        if (s.isEmpty()) {
            return null;
        }
        Iterator<ConstraintViolation<Object>> list = s.iterator();
        Map<String, String> item = new HashMap<>();
        String errMsg = "";
        while (list.hasNext()) {
            ConstraintViolation<Object> col = list.next();
            errMsg = col.getMessage();
            String field = col.getPropertyPath().toString();
            item.put(field, errMsg);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("msg", errMsg);
        map.put("fields", item);
        return map;
    }
}
