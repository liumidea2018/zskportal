package com.haohe.zskportal.common.annotation;

import com.haohe.zskportal.common.validator.CronValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 11:15
 * @description
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CronValidator.class)
public @interface IsCron {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
