package com.heart.handler.security;

import com.alibaba.fastjson.JSON;
import com.heart.domain.ResponseResult;
import com.heart.enums.AppHttpCodeEnum;
import com.heart.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        // 若请求中没有携带 token，则应该报用户未登录异常而不是用户名密码错误
        String token = request.getHeader("token");
        ResponseResult errorResult = null;
        if(!StringUtils.hasText(token)){
            String uri = request.getRequestURI();
            if(uri.contains("login")){
                // 如果是登录请求，那么肯定没有 token，应该返回的是用户名或密码错误
                // 发生认证异常时，将异常信息填入 responseResult
                errorResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
                WebUtils.renderString(response, JSON.toJSONString(errorResult));
                return;
            }
            // 如果不是登录请求，但又没有携带 token,那么就返回需要登录
            // 发生认证异常时，将异常信息填入 responseResult
            errorResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(errorResult));
            return;
        }
        // 如果存在 token,那么要返回当前权限不足的响应
        errorResult = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        WebUtils.renderString(response, JSON.toJSONString(errorResult));
    }
}
