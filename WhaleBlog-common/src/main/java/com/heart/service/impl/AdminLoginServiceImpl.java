package com.heart.service.impl;

import com.heart.constants.redis.RedisConstants;
import com.heart.domain.ResponseResult;
import com.heart.domain.dto.user.UserDto;
import com.heart.domain.entity.LoginUser;
import com.heart.domain.entity.User;
import com.heart.domain.vo.user.LoginUserVo;
import com.heart.service.AdminLoginService;
import com.heart.utils.JwtUtil;
import com.heart.utils.RedisCache;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult login(UserDto userDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDto.getUserName(),
                userDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 通过 authentication 得到 loginUser
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 判断 loginUser是否为空
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户名或密码错误");
        }
        User user = loginUser.getUser();
        String userId = String.valueOf(user.getId());
        String token = JwtUtil.createJWT(userId);
        // 若 loginUser 不为空,则登录成功，需要将 id 存入 redis
        // key 为 adminlogin: + userId
        redisCache.setCacheObject(RedisConstants.REDIS_ADMIN_LOGIN_KEY + userId, loginUser);
        // 将用户信息传入 redis 之后，只需要封装 token,然后返回
        HashMap<String,String> map = new HashMap<>();
        map.put("token",token);
        return ResponseResult.okResult(map);
    }
}
