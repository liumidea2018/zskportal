package com.haohe.zskportal.sys.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 14:17
 * @description
 */
@Data
@Table(name = "p_permission_menu")
public class PermissionMenu implements Serializable {

    private static final long serialVersionUID = 7988228213254148348L;

    /**
     * 自增主键
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer  id;
    /**
     * 权限主键
     */
    private Integer  permissionId;
    /**
     * 资源主键
     */
    private Integer  menuId;

}
