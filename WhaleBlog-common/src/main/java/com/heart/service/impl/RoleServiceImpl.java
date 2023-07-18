package com.heart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heart.domain.entity.Role;
import com.heart.mapper.RoleMapper;
import com.heart.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author Heart
 * @since 2023-07-16 19:31:55
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<String> selectRoleKeyByUserId(Long userId) {
        return roleMapper.selectRoleKeyByUserId(userId);
    }
}

