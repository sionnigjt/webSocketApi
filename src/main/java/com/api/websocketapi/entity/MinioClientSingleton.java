package com.api.websocketapi.entity;

import io.minio.MinioClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * minio客户端单例类
 *
 * @author sion
 */
@Getter
@Component
public class MinioClientSingleton {

    private static String domainUrl;
    private static String accessKey;
    private static String secretKey;

    @Value("${min-io.endpoint}")
    public void setDomainUrl(String domainUrl) {
        MinioClientSingleton.domainUrl = domainUrl;
    }

    @Value("${min-io.accessKey}")
    public void setAccessKey(String accessKey) {
        MinioClientSingleton.accessKey = accessKey;
    }

    @Value("${min-io.secretKey}")
    public void setSecretKey(String secretKey) {
        MinioClientSingleton.secretKey = secretKey;
    }

    private volatile static MinioClient minioClient;

    public MinioClientSingleton() {
    }

    /**
     * 获取minio客户端实例
     *
     * @return {@link MinioClient}
     */
    public static MinioClient getMinioClient() {
        if (minioClient == null) {
            synchronized (MinioClientSingleton.class) {
                if (minioClient == null) {
                    minioClient = MinioClient.builder()
                            .endpoint(domainUrl)
                            .credentials(accessKey, secretKey)
                            .build();
                }
            }
        }
        return minioClient;
    }
}