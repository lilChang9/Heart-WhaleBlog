package com.heart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heart.domain.ResponseResult;
import com.heart.domain.dto.user.UserInfoDto;
import com.heart.domain.entity.User;
import com.heart.domain.vo.user.UserInfoVo;
import com.heart.enums.AppHttpCodeEnum;
import com.heart.exception.SystemException;
import com.heart.mapper.UserMapper;
import com.heart.service.UserService;
import com.heart.utils.BeanCopyUtils;
import com.heart.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户表(User)表服务实现类
 *
 * @author Heart
 * @since 2023-06-23 15:37:10
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult getUserInfo(HttpServletRequest request) {
        // 查询用户信息
        // 为了保证安全性，应该从请求体中拿到 token
        // 将 token 解析成 userId 查询用户信息
        String token = request.getHeader("token");
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String userId = claims.getSubject();
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult register(UserInfoDto userInfoDto) {
        // 对用户注册信息进行非空判断
        if (!StringUtils.hasText(userInfoDto.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(userInfoDto.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        if (!StringUtils.hasText(userInfoDto.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
        }
        if (!StringUtils.hasText(userInfoDto.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
        }

        // 对数据进行重复校验
        if (usernameExist(userInfoDto.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(userInfoDto.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (emailExist(userInfoDto.getUsername())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        String password = userInfoDto.getPassword();
        // 对密码进行加密
        String encodedPassword = passwordEncoder.encode(password);
        userInfoDto.setPassword(encodedPassword);
        User user = BeanCopyUtils.copyBean(userInfoDto, User.class);
        user.setUserName(userInfoDto.getUsername());
        save(user);
        return ResponseResult.okResult("注册成功");
    }

    private boolean usernameExist(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        return count(queryWrapper) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper) > 0;
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }
}

