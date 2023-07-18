package com.heart.filter;

import com.alibaba.fastjson.JSON;
import com.heart.constants.redis.RedisConstants;
import com.heart.domain.ResponseResult;
import com.heart.domain.entity.LoginUser;
import com.heart.enums.AppHttpCodeEnum;
import com.heart.utils.JwtUtil;
import com.heart.utils.RedisCache;
import com.heart.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 对请求中的token进行校验
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 首先从请求头中获取 token 信息
        String token = request.getHeader("token");
        // 但前端发起的请求中可能不携带 token 信息
        // 也就是访问的资源不需要 token 认证
        // 对这种情况，应该放行
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return ;
        }
        // 如果 token 不为空，那么要对 token 进行解析
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // 解析 token 时可能失效
            // 两种原因:
            // (1) token超时 (2) token非法
            // 对于这两种情况，都应该响应给前端
            ResponseResult errorResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            // 将响应结果封装到 response 中返回
            String json = JSON.toJSONString(errorResult);
            WebUtils.renderString(response,json);
            return ;
        }
        // token 合法且有效时,从 redis 中获取登录用户信息
        // 解析完的 token 应该是 userId
        String userId = claims.getSubject();
        // 从 redis 中获取 loginUser
        LoginUser loginUser = redisCache.getCacheObject(RedisConstants.REDIS_ADMIN_LOGIN_KEY + userId);
        // 如果 loginUser 为 null,也需要提示需要重新登录
        if(Objects.isNull(loginUser)){
            ResponseResult errorResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            // 将响应结果封装到 response 中返回
            String json = JSON.toJSONString(errorResult);
            WebUtils.renderString(response,json);
            return ;
        }
        // 必须用三个参数的 UsernamePasswordAuthenticationToken 构造方法，因为三个参数的构造方法会将认证状态设置为 true
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        // 将 loginUser 写入 SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request,response);
    }
}
