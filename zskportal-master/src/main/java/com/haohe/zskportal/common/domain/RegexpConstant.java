package com.haohe.zskportal.common.domain;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 12:22
 * @description 正则常量
 */
public class RegexpConstant {

    // 简单手机号正则
    public static final String MOBILE_REG = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
    //邮箱
    public static final String EMAIL_REG = "[A-z]+[A-z0-9_-]*\\@[A-z0-9]+\\.[A-z]+";


}
