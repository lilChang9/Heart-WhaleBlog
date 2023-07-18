package com.heart.controller;

import com.heart.domain.ResponseResult;
import com.heart.domain.entity.LoginUser;
import com.heart.domain.entity.User;
import com.heart.domain.vo.AdminUserInfoVo;
import com.heart.domain.vo.UserInfoVo;
import com.heart.service.MenuService;
import com.heart.service.RoleService;
import com.heart.utils.BeanCopyUtils;
import com.heart.utils.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限信息 Controller 层
 */
@RestController
public class PermissionController {

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        Long userId = user.getId();
        // 根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(userId);
        // 根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(userId);
        // 用户信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(new AdminUserInfoVo(perms,roleKeyList,userInfoVo));
    }
}
