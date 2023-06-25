package com.heart.handler.exception;

import com.heart.domain.ResponseResult;
import com.heart.enums.AppHttpCodeEnum;
import com.heart.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandle(SystemException e){
        // 打印异常信息
        log.error("出现异常,异常信息为:{}",e.getMsg());
        // 将异常结果封装成 ResponseResult 返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandle(Exception e){
        // 打印异常信息
        log.error("出现异常,异常信息为:{}",e.getMessage());
        // 将异常结果封装成 ResponseResult 返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"系统错误");
    }


}
