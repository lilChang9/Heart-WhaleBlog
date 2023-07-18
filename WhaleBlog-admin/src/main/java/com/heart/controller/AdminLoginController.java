package com.heart.controller;

import com.heart.domain.ResponseResult;
import com.heart.domain.dto.user.UserDto;
import com.heart.enums.AppHttpCodeEnum;
import com.heart.exception.SystemException;
import com.heart.service.AdminLoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class AdminLoginController {

    @Resource
    private AdminLoginService adminLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserDto userDto){
        // 判断是否传入了用户名
        if(!StringUtils.hasText(userDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(userDto);
    }

}
