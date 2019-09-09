package com.haohe.zskportal.sys.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 13:55
 * @description 菜单表
 */
@Data
@Table(name = "p_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = -6691782405747185057L;

    /**
     * 自增主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer  id;
    /**
     * 父节点
     */
    private Integer  parentId;
    /**
     * 所属模块
     */
    private String  module;
    /**
     * 资源名
     */
    private String  name;
    /**
     *
     */
    private String  code;
    /**
     * 资源类型
     */
    private Byte  type;
    /**
     * 资源URL
     */
    private String  url;
    /**
     * 图标
     */
    private String  ico;
    /**
     * 排序号
     */
    private Integer  sortNo;

}
