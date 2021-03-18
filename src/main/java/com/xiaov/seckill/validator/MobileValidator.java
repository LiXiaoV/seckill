package com.xiaov.seckill.validator;

import com.xiaov.seckill.util.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Validator;

/**
 * @author xiaov
 * @since 2021-03-04 11:41
 */
public class MobileValidator implements ConstraintValidator<MobileCheck,String> {

    private boolean require = false;

    @Override
    public void initialize(MobileCheck constraintAnnotation) {
        require = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(require){
            return ValidatorUtil.isMobile(s);
        }else {
            if(StringUtils.isEmpty(s)){
                return true;
            }else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
