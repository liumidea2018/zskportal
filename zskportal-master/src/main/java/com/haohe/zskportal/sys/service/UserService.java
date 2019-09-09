package com.haohe.zskportal.sys.service;

import com.haohe.zskportal.sys.model.User;

/**
 * @author 微笑の掩饰
 * @date 2019/6/28 14:55
 * @description 用户服务
 */
public interface UserService {

    User getById(long id);

    void insert(User user);

    /**
     * 通过用户名查找用户
     * @param username username
     * @return user
     */
    User findByName(String username);

    /**
     * 更新用户登陆时间
     * @param user
     * @throws Exception
     */
    void updateLoginTime(User user) throws Exception;

    /**
     * 新增用户
     *
     * @param user user
     */
    void addUser(User user) throws Exception;

    /**
     * 修改用户
     *
     * @param user user
     */
    void updateUser(User user) throws Exception;

    /**
     * 删除用户
     *
     * @param userIds 用户 id数组
     */
    void deleteUsers(Integer[] userIds) throws Exception;

    /**
     * 更新个人信息
     *
     * @param user 个人信息
     */
    void updateProfile(User user) throws Exception;

    /**
     * 更新用户头像
     *
     * @param username 用户名
     * @param avatar   用户头像
     */
    void updateAvatar(String username, String avatar) throws Exception;

    /**
     * 更新用户密码
     *
     * @param username 用户名
     * @param password 新密码
     */
    void updatePassword(String username, String password) throws Exception;

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     */
    void regist(String username, String password) throws Exception;

    /**
     * 重置密码
     *
     * @param usernames 用户集合
     */
    void resetPassword(String[] usernames) throws Exception;

}
