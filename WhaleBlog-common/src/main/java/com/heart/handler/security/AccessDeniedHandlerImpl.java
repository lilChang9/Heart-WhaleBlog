package com.heart.handler.security;

import com.alibaba.fastjson.JSON;
import com.heart.domain.ResponseResult;
import com.heart.enums.AppHttpCodeEnum;
import com.heart.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        // 发生认证异常时，将异常信息填入 responseResult
        ResponseResult errorResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        WebUtils.renderString(response, JSON.toJSONString(errorResult));
    }
}
