package com.haohe.zskportal.common.function;

import com.haohe.zskportal.common.exception.RedisConnectException;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 13:22
 * @description
 */
@FunctionalInterface
public interface JedisExecutor<T, R>  {
    R excute(T t) throws RedisConnectException;
}
