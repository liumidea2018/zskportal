package com.haohe.zskportal.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author 微笑の掩饰
 * @date 2019/6/26 16:44
 * @description
 *      Mapper接口：基本的增、删、改、查方法
 *      MySqlMapper：针对MySQL的额外补充接口，支持批量插入
 *      所有的其他自定义的mapper接口必须继承此接口
 */
public interface AutoMapper<T> extends Mapper<T>,MySqlMapper<T> {

}
