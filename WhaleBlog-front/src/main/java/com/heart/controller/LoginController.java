package com.heart.controller;

import com.heart.domain.ResponseResult;
import com.heart.domain.dto.user.UserDto;
import com.heart.enums.AppHttpCodeEnum;
import com.heart.exception.SystemException;
import com.heart.service.LoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserDto userDto){
        // 判断是否传入了用户名
        if(!StringUtils.hasText(userDto.getUsername())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(userDto);
    }

    @PostMapping("/logout")
    public ResponseResult logout(HttpServletRequest request){
        return loginService.logout(request);
    }


}
