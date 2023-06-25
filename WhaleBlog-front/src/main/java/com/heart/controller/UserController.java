package com.heart.controller;

import com.heart.annotation.SystemLog;
import com.heart.domain.ResponseResult;
import com.heart.domain.dto.user.UserInfoDto;
import com.heart.domain.entity.User;
import com.heart.domain.vo.user.UserInfoVo;
import com.heart.service.UserService;
import com.heart.utils.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult getUserInfo(HttpServletRequest request){
        return userService.getUserInfo(request);
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody UserInfoVo userInfoVo){
        User user = BeanCopyUtils.copyBean(userInfoVo, User.class);
        userService.updateById(user);
        return ResponseResult.okResult("用户个人信息更新成功");
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserInfoDto userInfoDto){
        return userService.register(userInfoDto);
    }
}
