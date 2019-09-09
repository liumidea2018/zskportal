package com.haohe.zskportal.common.domain;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author 微笑の掩饰
 * @date 2019/7/3 11:12
 * @description 返回消息封装类
 */
public class ZskResponse extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = -7733039700520099968L;

    public ZskResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public ZskResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public ZskResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
