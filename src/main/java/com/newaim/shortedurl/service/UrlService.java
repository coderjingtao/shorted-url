package com.newaim.shortedurl.service;

import org.springframework.scheduling.annotation.Async;

/**
 * @author Joseph.Liu
 */
public interface UrlService {

    String getOriginalUrl(String shortUrl);

    String saveUrlMap(String shortUrl, String longUrl, String originalUrl);

    String getAllUrlMaps();

    @Async
    void updateUrlViews(String shortUrl);
}
