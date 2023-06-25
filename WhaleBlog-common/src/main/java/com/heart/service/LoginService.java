package com.heart.service;

import com.heart.domain.ResponseResult;
import com.heart.domain.dto.user.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    ResponseResult login(UserDto userDto);

    ResponseResult logout(HttpServletRequest request);

}
