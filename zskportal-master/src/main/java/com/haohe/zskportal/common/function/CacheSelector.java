package com.haohe.zskportal.common.function;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 12:06
 * @description
 */
@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
