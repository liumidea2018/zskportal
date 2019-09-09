package com.haohe.zskportal.sys.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 14:06
 * @description 角色数据权限中间表
 */
@Data
@Table(name = "p_role_data_access")
public class RoleDataAccess implements Serializable {

    private static final long serialVersionUID = -5556281820868120012L;

    /**
     * 自增主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer  id;
    /**
     * 角色主键
     */
    private Integer  roleId;
    /**
     * 数据访问权限主键
     */
    private Integer  dataAccessId;
}
