package com.haohe.zskportal.common.validator;

import com.haohe.zskportal.common.annotation.IsCron;
import org.apache.logging.log4j.core.util.CronExpression;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 11:17
 * @description 校验是否为合法的 Cron表达式
 */
public class CronValidator implements ConstraintValidator<IsCron, String> {
    @Override
    public void initialize(IsCron isCron) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return CronExpression.isValidExpression(value);
        } catch (Exception e) {
            return false;
        }
    }
}
