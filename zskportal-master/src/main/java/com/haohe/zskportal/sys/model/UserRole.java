package com.haohe.zskportal.sys.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 微笑の掩饰
 * @date 2019/7/3 11:44
 * @description 用户角色关联表
 */
@Data
@Table(name = "p_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 7815242888025347919L;

    private Integer id;
    private Integer userId;
    private Integer roleId;

}
