package com.heart.service;

import com.heart.domain.ResponseResult;
import com.heart.domain.dto.user.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface AdminLoginService {

    ResponseResult login(UserDto userDto);

}
