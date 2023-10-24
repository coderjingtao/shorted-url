package com.newaim.shortedurl.controller;

import com.joseph.core.util.StrUtil;
import com.newaim.shortedurl.common.Result;
import com.newaim.shortedurl.service.UrlService;
import com.newaim.shortedurl.util.UrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author Joseph.Liu
 */
@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    private String host;

    @Value("${server.address}")
    public void setHost(String host){
        this.host = host;
    }

    @PostMapping("/generate")
    public Result<String> generateShortUrl(@RequestParam("originalUrl") String originalUrl){
        if(UrlUtil.isValidUrl(originalUrl)){
            if(!originalUrl.startsWith("http")){
                originalUrl = "http://"+originalUrl;
            }
            String shortUrl = urlService.saveUrlMap(StrUtil.hashToBase62(originalUrl), originalUrl, originalUrl);
            return Result.success(200,"success",host + "/" + shortUrl);
        }
        return Result.failed(500,"Incorrect URL format",StrUtil.EMPTY);
    }

    @GetMapping("/all")
    public Result<String> getAllUrlMaps(){
        String allUrlMaps = urlService.getAllUrlMaps();
        return Result.success(200,"success",allUrlMaps);
    }
}
