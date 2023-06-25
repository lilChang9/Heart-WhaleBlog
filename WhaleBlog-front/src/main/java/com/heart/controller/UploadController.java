package com.heart.controller;

import com.heart.domain.ResponseResult;
import com.heart.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult upload(HttpServletRequest request,MultipartFile img){
        return uploadService.upload(request,img);
    }
}
