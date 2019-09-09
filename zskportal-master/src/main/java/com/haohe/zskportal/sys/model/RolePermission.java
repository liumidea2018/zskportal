package com.haohe.zskportal.sys.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 14:03
 * @description 角色权限表
 */
@Data
@Table(name = "p_role_permission")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = -2384074779864713262L;

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
     * 权限主键
     */
    private Integer  permissionId;


}
