package com.haohe.zskportal.sys.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 14:13
 * @description 权限表
 */
@Data
@Table(name = "p_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = -2443822381719717958L;

    /**
     * 自增主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer  id;
    /**
     * 权限名
     */
    private String  name;
    /**
     * 权限编码
     */
    private String  code;
    /**
     * 是否内置
     */
    private Byte  isSys;
    /**
     * 是否删除
     */
    private Byte  isDel;
    /**
     * 创建者
     */
    private Integer  creator;
    /**
     * 创建时间
     */
    private Date createDate;

}
