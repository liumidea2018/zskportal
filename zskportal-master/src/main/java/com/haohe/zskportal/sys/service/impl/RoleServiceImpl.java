package com.haohe.zskportal.sys.service.impl;

import com.haohe.zskportal.sys.model.Role;
import com.haohe.zskportal.sys.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 微笑の掩饰
 * @date 2019/6/28 15:11
 * @description
 */
@Service
public class RoleServiceImpl implements RoleService {


    @Override
    public List<Role> findUserRole(Integer userId) {
        return null;
    }

    @Override
    public Role findByName(String roleName) {
        return null;
    }

    @Override
    public void createRole(Role role) {

    }

    @Override
    public void deleteRoles(String[] roleIds) throws Exception {

    }

    @Override
    public void updateRole(Role role) throws Exception {

    }
}
