package com.heart.service;

import com.heart.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UploadService {

    public ResponseResult upload(HttpServletRequest request,MultipartFile file);
}
