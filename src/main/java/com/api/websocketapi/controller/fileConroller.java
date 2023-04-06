package com.api.websocketapi.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author sion
 */
@RestController
@RequestMapping("/api/file")
public class fileConroller {
    private static final String FILE_NAME_PATTERN = "{}_{}";
    private MinioClient minioClient = null;

    @RequestMapping("/upload")
    public ResponseEntity<Boolean> upload(MultipartFile file) {
        // 上传文件的原始文件名
        String originalFilename = file.getOriginalFilename();
        // 文件大小
        long fileSize = file.getSize();
       // 文件名：日期_原始文件名
        String fileName = StrUtil.format(FILE_NAME_PATTERN, DateUtil.format(new Date(), "yyyyMMddHHmmss"), originalFilename);
        try {
            minioClient =  MinioClient.builder()
                    .endpoint("http://47.108.56.136:9090")
                    .credentials("admin", "1935264450Ai")
                    .build();
//            System.out.println(minioClient.bucketExists(BucketExistsArgs.builder());
            minioClient.putObject(PutObjectArgs.builder().bucket("img")
                    .object(fileName).stream(file.getInputStream(), fileSize, -1).contentType(file.getContentType()).build());
        } catch (Exception e) {

            return (ResponseEntity<Boolean>) ResponseEntity.status(Integer.parseInt("500"));
        }
        return (ResponseEntity<Boolean>)ResponseEntity.ok();
    }

}
