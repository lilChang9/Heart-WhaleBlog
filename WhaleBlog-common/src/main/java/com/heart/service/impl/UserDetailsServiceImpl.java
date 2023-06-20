package com.heart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heart.domain.entity.LoginUser;
import com.heart.domain.entity.User;
import com.heart.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据传进来的 username 查询数据库中是否存在
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(userWrapper);
        if(Objects.isNull(user)){
            // 如果未查到当前用户信息,返回异常
            throw new RuntimeException("当前用户不存在");
        }
        // 如果当前用户存在,将其封装到 UserDetails 中
        // TODO 之后可能还要进行用户权限的封装
        return new LoginUser(user);
    }
}
