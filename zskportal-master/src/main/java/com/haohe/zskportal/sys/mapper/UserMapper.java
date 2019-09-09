package com.haohe.zskportal.sys.mapper;

import com.haohe.zskportal.common.mapper.AutoMapper;
import com.haohe.zskportal.sys.model.User;
import com.haohe.zskportal.sys.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author 微笑の掩饰
 * @date 2019/6/26 16:49
 * @description
 */
@Mapper
@Repository
public interface UserMapper extends AutoMapper<User> {

    /**
     * 用户信息查询
     * @param username
     * @return
     */
    UserVO selectUserInfo(@Param("username") String username);

}
