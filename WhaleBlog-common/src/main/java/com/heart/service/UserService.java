package com.heart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heart.domain.ResponseResult;
import com.heart.domain.dto.user.UserInfoDto;
import com.heart.domain.entity.User;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户表(User)表服务接口
 *
 * @author Heart
 * @since 2023-06-23 15:37:10
 */
public interface UserService extends IService<User> {

    ResponseResult getUserInfo(HttpServletRequest request);

    ResponseResult register(UserInfoDto userInfoDto);
}
