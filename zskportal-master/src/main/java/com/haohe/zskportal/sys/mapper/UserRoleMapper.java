package com.haohe.zskportal.sys.mapper;

import com.haohe.zskportal.common.mapper.AutoMapper;
import com.haohe.zskportal.sys.model.User;
import com.haohe.zskportal.sys.model.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 微笑の掩饰
 * @date 2019/7/3 11:43
 * @description
 */
@Mapper
@Repository
public interface UserRoleMapper extends AutoMapper<UserRole> {
}
