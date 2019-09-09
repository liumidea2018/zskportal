package com.haohe.zskportal.common.exception;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 11:33
 * @description 限流异常
 */
public class LimitAccessException extends Exception {

    private static final long serialVersionUID = -3608667856397125671L;

    public LimitAccessException(String message) {
        super(message);
    }

}
