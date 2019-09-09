package com.haohe.zskportal.sys.manager;

import com.haohe.zskportal.common.service.CacheService;
import com.haohe.zskportal.sys.model.Role;
import com.haohe.zskportal.sys.model.User;
import com.haohe.zskportal.sys.service.UserService;
import com.haohe.zskportal.utils.ZskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 微笑の掩饰
 * @date 2019/6/28 14:54
 * @description
 */
@Component
public class UserManager {
    @Autowired
    private CacheService cacheService;

    @Autowired
    private UserService userService;


    /**
     * 通过用户名获取用户基本信息
     *
     * @param username 用户名
     * @return 用户基本信息
     */
    public User getUser(String username) {
        return ZskUtil.selectCacheByTemplate(
                () -> cacheService.getUser(username),
                () -> userService.findByName(username));
    }

    /**
     * 将用户相关信息添加到 Redis缓存中
     *
     * @param user user
     */
    public void loadUserRedisCache(User user) throws Exception {
        // 缓存用户
        cacheService.saveUser(user);
    }

    /**
     * 将用户角色和权限添加到 Redis缓存中
     *
     * @param userIds userIds
     */
//    public void loadUserPermissionRoleRedisCache(List<Integer> userIds) throws Exception {
//        for (Integer userId : userIds) {
//            User user = userService.getById(userId);
//            // 缓存用户角色
////            cacheService.saveRoles(user.getUsername());
//            // 缓存用户权限
////            cacheService.savePermissions(user.getUsername());
//        }
//    }

    /**
     * 通过用户 id集合批量删除用户 Redis缓存
     *
     * @param userIds userIds
     */
    public void deleteUserRedisCache(Integer... userIds) throws Exception {
        for (Integer userId : userIds) {
            User user = userService.getById(userId);
            if (user != null) {
                cacheService.deleteUser(user.getUsername());
//                cacheService.deleteRoles(user.getUsername());
            }
        }
    }
}
