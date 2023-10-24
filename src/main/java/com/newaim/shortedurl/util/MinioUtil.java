package com.newaim.shortedurl.util;

import com.joseph.core.util.StrUtil;
import com.newaim.shortedurl.common.MinioConfiguration;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author Joseph.Liu
 */
@Component
@RequiredArgsConstructor
public final class MinioUtil {

    private final MinioClient minioClient;
    private final MinioConfiguration config;

    /**
     * 获取默认bucket下的文件流
     * @param fileName 文件名称
     * @return 二进制流
     */
    @SneakyThrows
    public InputStream getFileInputStream(String fileName){
        GetObjectArgs objectArgs = GetObjectArgs.builder()
                .bucket(config.getBucket())
                .object(fileName)
                .build();
        return minioClient.getObject(objectArgs);
    }

    /**
     * 通过InputStream流上传文件
     * @param fileName 文件名
     * @param inputStream 文件内容流
     */
    @SneakyThrows
    public void uploadFile(String fileName, InputStream inputStream){
        PutObjectArgs objectArgs = PutObjectArgs.builder()
                .bucket(config.getBucket())
                .object(fileName)
                .stream(inputStream,inputStream.available(),-1)
                .build();
        minioClient.putObject(objectArgs);
    }

    /**
     * 通过MultipartFile进行文件上传
     * @param fileName 文件名
     * @param file MultipartFile in form-data on frontend page
     * @return the final access address of the file on MinIO
     */
    @SneakyThrows
    public String uploadFile(String fileName, MultipartFile file){
        InputStream inputStream = file.getInputStream();
        PutObjectArgs objectArgs = PutObjectArgs.builder()
                .bucket(config.getBucket())
                .object(fileName)
                .stream(inputStream, inputStream.available(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(objectArgs);
        return String.format("%s/%s/%s",config.getEndpoint(),config.getBucket(),fileName);
    }

    /**
     * 图片上传
     * @param imageName 图片名
     * @param imageBase64 图片内容的Base64编码，对于一些小的图片是极为方便的，因为你不需要再去寻找一个保存图片的地方
     * 在前端插入图片时，直接使用Base64编码即可
     * CSS中使用：
     * background-image: url("data:image/png;base64,iVBORw0KGgo=...");
     * HTML中使用：
     * <img src="data:image/png;base64,iVBORw0KGgo=..." />
     */
    public void uploadImage(String imageName, String imageBase64){
        if(StrUtil.isBlank(imageBase64)){
            return;
        }
        InputStream inputStream = base64ToInputStream(imageBase64);
        uploadFile(imageName,inputStream);
    }

    public static InputStream base64ToInputStream(String base64){
        byte[] bytes = Base64.getDecoder().decode(base64);
        return new ByteArrayInputStream(bytes);
    }

    @SneakyThrows
    public static String fileToBase64(String filePath){
        FileInputStream fis = new FileInputStream(filePath);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        fis.close();
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 拷贝文件
     * @param srcBucket 源文件所在bucket
     * @param srcFileName 源文件
     * @param destBucket 目标文件所在bucket
     * @param destFileName 目标文件
     */
    @SneakyThrows
    public void copyFile(String srcBucket, String srcFileName, String destBucket, String destFileName){
        CopySource copySource = CopySource.builder()
                .bucket(srcBucket)
                .object(srcFileName)
                .build();

        CopyObjectArgs objectArgs = CopyObjectArgs.builder()
                .source(copySource)
                .bucket(destBucket)
                .object(destFileName)
                .build();

        minioClient.copyObject(objectArgs);
    }

    /**
     * 获取文件信息，如果抛异常则说明文件不存在
     * @param fileName 文件名
     * @return 文件信息
     */
    @SneakyThrows
    public String getFileInfo(String fileName){
        StatObjectArgs objectArgs = StatObjectArgs.builder()
                .bucket(config.getBucket())
                .object(fileName)
                .build();
        return minioClient.statObject(objectArgs).toString();
    }

    @SneakyThrows
    public void removeFile(String fileName){
        RemoveObjectArgs objectArgs = RemoveObjectArgs.builder()
                .bucket(config.getBucket())
                .object(fileName)
                .build();
        minioClient.removeObject(objectArgs);
    }

}
