package com.api.websocketapi.service.impl;


import com.api.websocketapi.service.MinIOService;
import com.api.websocketapi.utils.MinIOUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;


@Service
public class MinIOServiceImpl  implements MinIOService {
    @Resource
    private MinIOUtils minIOUtils;

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName
     * @return
     */
    @Override
    public boolean bucketExists(String bucketName) {
        return minIOUtils.bucketExists(bucketName);
    }

    /**
     * 创建 bucket
     *
     * @param bucketName
     */
    @Override
    public void makeBucket(String bucketName) {
        minIOUtils.makeBucket(bucketName);
    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param objectName
     * @param filename
     */
    @Override
    public void putObject(String bucketName, String objectName, String filename) {
        minIOUtils.putObject(bucketName, objectName, filename);
    }


    @Override
    public void putObject(String bucketName, String objectName, InputStream stream, String contentType) {
        minIOUtils.putObject(bucketName, objectName, stream, contentType);
    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param multipartFile
     */
    @Override
    public void putObject(String bucketName, MultipartFile multipartFile, String filename) {
        minIOUtils.putObject(bucketName, multipartFile, filename);
    }

    /**
     * 删除文件
     * @param bucketName
     * @param filePath
     */
    @Override
    public boolean removeObject(String bucketName,String filePath) {
        return minIOUtils.removeObject(bucketName,filePath);
    }



    /**
     * 下载文件
     * @param bucketName
     * @param filePath
     * @param originalName
     * @param response
     * @return
     */
    @Override
    public boolean downloadFile(String bucketName, String filePath, String originalName, HttpServletResponse response) {
        return minIOUtils.downloadFile(bucketName,filePath, originalName, response);
    }

    /**
     * 获取文件路径
     * @param bucketName
     * @param objectName
     * @return
     */
    @Override
    public String getObjectUrl(String bucketName,String objectName) {
        return minIOUtils.getObjectUrl(bucketName,objectName);
    }
}
