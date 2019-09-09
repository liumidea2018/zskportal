package com.haohe.zskportal.common.exception;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 13:18
 * @description Redis 连接异常
 */
public class RedisConnectException extends Exception {

    private static final long serialVersionUID = 1639374111871115063L;

    public RedisConnectException(String message) {
        super(message);
    }

}
