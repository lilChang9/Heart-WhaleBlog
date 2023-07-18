package com.heart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heart.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author Heart
 * @since 2023-07-16 19:31:55
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}
