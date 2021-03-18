package com.xiaov.seckill.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author xiaov
 * @since 2021-03-04 11:33
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {MobileValidator.class}
)
public @interface MobileCheck {

    boolean required() default true;

    String message() default "手机号码格式有误!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
