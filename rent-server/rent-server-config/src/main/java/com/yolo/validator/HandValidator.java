package com.yolo.validator;




import com.yolo.validator.annotation.Vdata;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandValidator implements ConstraintValidator<Vdata, Object> {
    private boolean required = false;
    private VP vp;

    @Override
    public void initialize(Vdata constraintAnnotation) {
        this.required = constraintAnnotation.required();
        this.vp = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null && !required) {
            return true;
        }
        String str = value.toString();
        Pattern _pattern = Pattern.compile(vp.getPattern());
        Matcher m = _pattern.matcher(str);
        return m.matches();
    }
}
