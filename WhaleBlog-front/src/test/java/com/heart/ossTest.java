package com.heart;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
@Component
@Data
public class ossTest {

    private String accessKey;
    private String secretKey;
    private String bucket;

    @Test
    public void qiniuTest(){

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //String accessKey = QINIU_AK;
        //String secretKey = QINIU_SK;

        // 根据文件类型设置保存文件的文件夹
        File uploadFile = new File("C:\\Users\\cs1999\\Desktop\\headbg05.jpg");
        String path = uploadFile.getAbsolutePath();

        String bucket = "whale-blog";

        int dotIndex = path.lastIndexOf(".");
        String suffix = path.substring(dotIndex);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        StringBuilder sb = new StringBuilder();
        if(path.endsWith(".jpg") || path.endsWith(".png")){
            sb.append("imgs/");
            // 为了保证安全以及将对应用户的头像进行分类
            // 在imgs后添加用户id的 token
            /*String token = JwtUtil.createJWT(SecurityUtils.getUserId().toString());
            sb.append(token).append("/");*/
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(new Date());
            String[] splitDate = formattedDate.split("-");
            for (int i = 0; i < splitDate.length; i++) {
                sb.append(splitDate[i]).append("/");
            }
            sb.append(UUID.randomUUID());
            sb.append(suffix);
        }
        String key = sb.toString();

        byte[] uploadBytes = fileConvertToByteArray(uploadFile);
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return ;
    }

    /**
     * 把一个文件转化为byte字节数组。
     *
     * @return
     */
    private static byte[] fileConvertToByteArray(File file) {
        byte[] data = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            data = baos.toByteArray();

            fis.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
