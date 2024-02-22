package com.yolo.validator.annotation;




import com.yolo.validator.HandValidator;
import com.yolo.validator.VP;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Target({FIELD})
@Retention(RUNTIME)
@Repeatable(Vdata.List.class)
@Documented
@Constraint(validatedBy = {HandValidator.class})
public @interface Vdata {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //必须有值
    VP type();

    //是否必填字段
    boolean required() default false;

//    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Target({ FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Vdata[] value();
    }

}
