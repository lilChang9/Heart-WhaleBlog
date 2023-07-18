package com.heart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heart.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author Heart
 * @since 2023-07-16 19:31:55
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}
