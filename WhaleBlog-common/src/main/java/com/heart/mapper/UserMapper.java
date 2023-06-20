package com.heart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heart.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(User)表数据库访问层
 *
 * @author Heart
 * @since 2023-06-20 12:03:46
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
