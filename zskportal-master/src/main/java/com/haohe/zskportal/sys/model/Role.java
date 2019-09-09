package com.haohe.zskportal.sys.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 13:56
 * @description 角色表
 */
@Data
@Table(name = "p_role")
public class Role implements Serializable {

    private static final long serialVersionUID = -7796753181173822435L;

    /**
     * 自增主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer  id;
    /**
     * 角色名
     */
    private String  name;
    /**
     * 角色编号
     */
    private String  code;
    /**
     * 是否内置
     */
    private Byte  isSys;
    /**
     * 是否启用
     */
    private Byte  isEnable;
    /**
     * 是否删除
     */
    private Byte  isDel;
    /**
     * 创建者
     */
    private Integer  creator;
    /**
     * 创建日期
     */
    private Date createDate;

}
