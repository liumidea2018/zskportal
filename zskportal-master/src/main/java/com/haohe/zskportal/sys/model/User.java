package com.haohe.zskportal.sys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户表
 * @author jockerboby
 * @description 用户表
 */
@Data
@Table(name = "p_user")
public class User implements Serializable{

    private static final long serialVersionUID = -2791094902340112558L;

    // 默认密码
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 账户状态,0为已锁定
     */
    public static final String STATUS_VALID = "1";

    public static final String STATUS_LOCK = "0";

    /**
     * 自增主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer  id;
    /**
     * 用户名
     */
    private String  name;
    /**
     * 账号
     */
    private String  username;
    /**
     * 密码
     */
    private String  password;
    /**
     * 机构主键
     */
    private Integer mechanismId;
    /**
     * 微信名称
     */
    private String wcName;
    /**
     *展示密码
     */
    private String swPassword;

    /**
     * 大区主键
     */
    private Integer  subzoneId;
    /**
     * 所属医院
     */
    private Integer hospitalId;
    /**
     * 邮箱地址
     */
    private String  email;
    /**
     * 是否锁定
     */
    private Byte  isLocked;
    /**
     * 是否禁用
     */
    private Byte  isEnabled;
    /**
     * 是否过期
     */
    private Byte  isExpired;
    /**
     * 是否内置
     */
    @JsonIgnore
    private Byte  isSys;

}
