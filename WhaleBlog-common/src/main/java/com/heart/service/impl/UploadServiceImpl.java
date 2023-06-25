package com.heart.service.impl;

import com.google.gson.Gson;
import com.heart.constants.file.FileConstants;
import com.heart.domain.ResponseResult;
import com.heart.enums.AppHttpCodeEnum;
import com.heart.service.UploadService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
@Slf4j
@Service
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String externalLink;

    @Override
    public ResponseResult upload(HttpServletRequest request, MultipartFile file) {
        return uploadImg(request, file);
    }

    private ResponseResult uploadImg(HttpServletRequest request, MultipartFile file) {
        if (file.getSize() >= FileConstants.MAX_FILE_SIZE) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "上传文件过大,请上传小于5MB大小的文件");
        }
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本

        UploadManager uploadManager = new UploadManager(cfg);
        // 根据文件类型设置保存文件的文件夹
        File uploadFile = new File(file.getOriginalFilename());
        String path = uploadFile.getAbsolutePath();

        int dotIndex = path.lastIndexOf(".");
        String suffix = path.substring(dotIndex);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        StringBuilder sb = new StringBuilder();
        if (path.endsWith(".jpg") || path.endsWith(".png")) {
            sb.append("imgs/");
            // 为了保证安全以及将对应用户的头像进行分类
            // 在imgs后添加用户id的 token
            /*String token = request.getHeader("token");
            sb.append(token).append("/");*/
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(new Date());
            String[] splitDate = formattedDate.split("-");
            for (int i = 0; i < splitDate.length; i++) {
                sb.append(splitDate[i]).append("/");
            }
            sb.append(UUID.randomUUID().toString().replaceAll("-",""));
            sb.append(suffix);
        }
        String key = sb.toString();

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            InputStream inputStream = file.getInputStream();
            Response response = uploadManager.put(inputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return ResponseResult.okResult(externalLink + putRet.key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, "上传失败");
    }
}
