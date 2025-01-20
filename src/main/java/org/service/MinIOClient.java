package org.service;

import io.minio.*;
import io.minio.errors.*;
import java.io.*;
import java.net.URL;
import java.time.Duration;

public class MinIOClient {
    private static MinIOClient instance;
    private MinioClient minioClient;

    private MinIOClient(String endpoint, String accessKey, String secretKey) {
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    public static synchronized MinIOClient getInstance(String endpoint, String accessKey, String secretKey) {
        if (instance == null) {
            instance = new MinIOClient(endpoint, accessKey, secretKey);
        }
        return instance;
    }

    public boolean uploadFile(String bucketName, String objectName, String filePath) {
        try {
            File file = new File(filePath);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new FileInputStream(file), file.length(), -1)
                    .build());
            return true;
        } catch (Exception e) {
            System.out.println("Error uploading file: " + e.getMessage());
            return false;
        }
    }

    public boolean downloadFile(String bucketName, String objectName, String downloadPath) {
        try {
            minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
            return true;
        } catch (Exception e) {
            System.out.println("Error downloading file: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        String minioEndpoint = "124.223.85.176:9000";
        String minioAccessKey = "ROOTNAME";
        String minioSecretKey = "CHANGEME123";

        MinIOClient minioClient = MinIOClient.getInstance(minioEndpoint, minioAccessKey, minioSecretKey);

        String file_path = "../parser/data/Lenovo Tab M11 User Guide_20231127.pdf";
        String file_out_path = "../parser/data/out.pdf";
        String bucket_name = "documents";
        String object_name = "Guide_20231127.pdf";

        // 上传文件
        boolean uploadSuccess = minioClient.uploadFile(bucket_name, object_name, file_path);
        if (uploadSuccess) {
            System.out.println("File uploaded successfully.");
        } else {
            System.out.println("Failed to upload file.");
        }

        // 下载文件
        boolean downloadSuccess = minioClient.downloadFile(bucket_name, object_name, file_out_path);
        if (downloadSuccess) {
            System.out.println("File downloaded successfully.");
        } else {
            System.out.println("Failed to download file.");
        }
    }
}