package com.haohe.zskportal.sys.service;

import com.haohe.zskportal.sys.model.Role;

import java.util.List;

/**
 * @author 微笑の掩饰
 * @date 2019/6/28 15:08
 * @description 角色服务
 */
public interface RoleService {

    List<Role> findUserRole(Integer userId);

    Role findByName(String roleName);

    void createRole(Role role);

    void deleteRoles(String[] roleIds) throws Exception;

    void updateRole(Role role) throws Exception;

}
