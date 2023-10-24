package com.newaim.shortedurl.controller;

import com.joseph.core.lang.UUID;
import com.newaim.shortedurl.util.MinioUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Joseph.Liu
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final MinioUtil minioUtil;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        String filename = UUID.randomUUID(false).toString(true);
        return minioUtil.uploadFile(filename,file);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("fileName") String fileName){
        minioUtil.removeFile(fileName);
        log.info("{} deleted success",fileName);
    }
}
