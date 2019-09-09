package com.haohe.zskportal.sys.service.impl;

import com.haohe.zskportal.common.service.CacheService;
import com.haohe.zskportal.sys.manager.UserManager;
import com.haohe.zskportal.sys.mapper.UserMapper;
import com.haohe.zskportal.sys.mapper.UserRoleMapper;
import com.haohe.zskportal.sys.model.User;
import com.haohe.zskportal.sys.model.UserRole;
import com.haohe.zskportal.sys.service.UserService;
import com.haohe.zskportal.sys.vo.UserVO;
import com.haohe.zskportal.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Description:    测试demo
 * @Author:         liumidea
 * @CreateDate:     2019/6/25 9:55
 * @param:
 * @return:
 * @Version:        1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserManager userManager;

    @Override
    public User getById(long id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void insert(User user){
        userMapper.insert(user);
    }

    @Override
    public User findByName(String username) {
        return userMapper.selectUserInfo(username);
    }

    @Override
    @Transactional
    public void updateLoginTime(User user) throws Exception {
        // 重新将用户信息加载到 redis中
        cacheService.saveUser(user);
    }

    @Override
    @Transactional
    public void addUser(User user) throws Exception {
        userMapper.insert(user);
        // 将用户相关信息保存到 Redis中
        userManager.loadUserRedisCache(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) throws Exception {
        userMapper.updateByPrimaryKey(user);
        // 重新缓存用户信息
        cacheService.saveUser(user.getUsername());
    }

    /**
     * 非物理删除，设置为过期
     * @param userIds 用户 id数组
     * @throws Exception
     */
    @Override
    @Transactional
    public void deleteUsers(Integer[] userIds) throws Exception {
        User user = new User();
        user.setIsExpired(Byte.valueOf("0"));
        for (Integer id: userIds) {
            user.setId(id);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    @Override
    @Transactional
    public void updateProfile(User user) throws Exception {
        userMapper.updateByPrimaryKeySelective(user);
        // 重新缓存用户信息
        cacheService.saveUser(user.getUsername());
    }

    @Override
    public void updateAvatar(String username, String avatar) throws Exception {
        //TODO 用户个性化设置后期再做

        // 重新缓存用户信息
        cacheService.saveUser(username);
    }

    @Override
    public void updatePassword(String username, String password) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));

        userMapper.updateByPrimaryKeySelective(user);
        // 重新缓存用户信息
        cacheService.saveUser(username);
    }

    @Override
    @Transactional
    public void regist(String username, String password) throws Exception {

        //保存用户
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setUsername(username);
        userMapper.insert(user);
        //添加用户角色
        UserRole ur = new UserRole();
        ur.setUserId(user.getId());
        ur.setRoleId(8); // 注册用户角色 ID
        userRoleMapper.insert(ur);

        // 将用户相关信息保存到 Redis中
        userManager.loadUserRedisCache(user);
    }

    @Override
    @Transactional
    public void resetPassword(String[] usernames) throws Exception {
        for (String username : usernames) {
            User user = new User();
            user.setPassword(MD5Util.encrypt(username, User.DEFAULT_PASSWORD));
            userMapper.updateByPrimaryKeySelective(user);
            // 重新将用户信息加载到 redis中
            cacheService.saveUser(username);
        }
    }
}
