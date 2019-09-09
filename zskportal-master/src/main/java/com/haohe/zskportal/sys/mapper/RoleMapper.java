package com.haohe.zskportal.sys.mapper;

import com.haohe.zskportal.common.mapper.AutoMapper;
import com.haohe.zskportal.sys.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author 微笑の掩饰
 * @date 2019/6/28 15:12
 * @description
 */
@Mapper
public interface RoleMapper extends AutoMapper<User> {

}
