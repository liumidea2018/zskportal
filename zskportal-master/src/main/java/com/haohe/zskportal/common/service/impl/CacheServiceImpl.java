package com.haohe.zskportal.common.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haohe.zskportal.common.domain.ZskConstant;
import com.haohe.zskportal.common.service.CacheService;
import com.haohe.zskportal.common.service.RedisService;
import com.haohe.zskportal.sys.model.User;
import com.haohe.zskportal.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 微笑の掩饰
 * @date 2019/6/28 15:06
 * @description
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisService redisService;

//    @Autowired
//    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void testConnect() throws Exception {
        redisService.exists("test");
    }

    @Override
    public User getUser(String username) throws Exception {
        String userString = redisService.get(ZskConstant.USER_CACHE_PREFIX + username);
        if (StringUtils.isBlank(userString)){
            throw new Exception("redis中没有该用户缓存信息");
        } else{
            return mapper.readValue(userString, User.class);
        }
    }

    @Override
    public void saveUser(User user) throws Exception {
        this.deleteUser(user.getUsername());
        redisService.set(ZskConstant.USER_CACHE_PREFIX + user.getUsername(), mapper.writeValueAsString(user));
    }

    @Override
    public void saveUser(String username) throws Exception {
        User user = userService.findByName(username);
        this.deleteUser(username);
        redisService.set(ZskConstant.USER_CACHE_PREFIX + username, mapper.writeValueAsString(user));
    }

    @Override
    public void deleteUser(String username) throws Exception {
        username = username.toLowerCase();
        redisService.del(ZskConstant.USER_CACHE_PREFIX + username);
    }

}
