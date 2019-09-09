package com.haohe.zskportal.common.exception;

import java.io.Serializable;

/**
 * @author 微笑の掩饰
 * @date 2019/7/3 11:16
 * @description 系统内部异常
 */
public class ZskException extends Exception implements Serializable {

    private static final long serialVersionUID = -9144767595373689265L;

    public ZskException(String message) {
        super(message);
    }
}
