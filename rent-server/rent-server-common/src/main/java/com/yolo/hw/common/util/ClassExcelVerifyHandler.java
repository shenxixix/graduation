package com.yolo.hw.common.util;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import lombok.SneakyThrows;

import java.util.Objects;

//@Component
public class ClassExcelVerifyHandler implements IExcelVerifyHandler<Object> {
    @SneakyThrows
    @Override
    public ExcelVerifyHandlerResult verifyHandler(Object obj) {
        ExcelVerifyHandlerResult result=new ExcelVerifyHandlerResult(true);
        if (!Objects.isNull(obj)){
            //判断对象属性是否全部为空
            boolean b = ObjectIsNullUitl.checkFieldAllNull(obj);
            result.setSuccess(!b);
        }
        return result;
    }
}
