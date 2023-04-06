package com.api.websocketapi.controller;
import com.api.websocketapi.config.MinIOConfig;
import com.api.websocketapi.service.MinIOService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RequestMapping("/api/file")
@RestController
@Slf4j
public class MinIOController {
    @Autowired
    private MinIOService minIOService;

    @Autowired
    private MinIOConfig minioConfig;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(MultipartFile file, String bucketName) {
        try {
            bucketName = !StringUtils.isEmpty(bucketName) ? bucketName : minioConfig.getBucketName();
            if (!minIOService.bucketExists(bucketName)) {
                minIOService.makeBucket(bucketName);
            }
            String fileName = file.getOriginalFilename();
            assert fileName != null;

            // 根据业务设计，设置存储路径：按天创建目录
            String objectName = new SimpleDateFormat("yyyy-MM-dd/").format(new Date())
                    + UUID.randomUUID().toString()
                    + fileName.substring(fileName.lastIndexOf("."));

            minIOService.putObject(bucketName, file, objectName);
            log.info("文件格式为:{}", file.getContentType());
            log.info("文件原名称为:{}", fileName);
            log.info("文件存储的桶为:{}", bucketName);
            log.info("文件对象路径为:{}", objectName);
            return ResponseEntity.ok(minIOService.getObjectUrl(bucketName, objectName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(String bucketName, String filePath) throws Exception {
        if (!minIOService.bucketExists(bucketName)) {
            throw new Exception("不存在该桶");
        }
        boolean status = minIOService.removeObject(bucketName, filePath);
        return status ? ResponseEntity.ok("删除成功") : ResponseEntity.ok("删除失败");
    }

    @PostMapping("/downloadFile")
    public ResponseEntity<String> downloadFile(String bucketName, String filePath, String originalName, HttpServletResponse response) throws Exception {
        if (!minIOService.bucketExists(bucketName)) {
            throw new Exception("不存在该桶");
        }
        boolean status = minIOService.downloadFile(bucketName, filePath, originalName, response);
        return status ? ResponseEntity.ok("下载成功") : ResponseEntity.ok("下载失败");
    }
}
