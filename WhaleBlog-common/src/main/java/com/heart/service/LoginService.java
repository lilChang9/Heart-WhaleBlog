package com.heart.service;

import com.heart.domain.ResponseResult;
import com.heart.domain.dto.user.UserDto;

public interface LoginService {
    ResponseResult login(UserDto userDto);
}
