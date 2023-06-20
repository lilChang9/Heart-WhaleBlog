package com.heart.controller;

import com.heart.domain.ResponseResult;
import com.heart.domain.dto.user.UserDto;
import com.heart.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserDto userDto){
        return loginService.login(userDto);
    }
}
