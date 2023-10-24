package com.newaim.shortedurl;

import com.joseph.core.util.StrUtil;
import com.newaim.shortedurl.util.UrlUtil;
import org.junit.jupiter.api.Test;


/**
 * @author Joseph.Liu
 */
public class UtilTest {

    @Test
    public void testHash(){
        String url = "www.google.com.au";
        String shortUrl = StrUtil.hashToBase62(url);
        System.out.println("shortUrl = " + shortUrl);
    }

    @Test
    public void testOriginalUrl(){
        String originalUrl = "www.newaim.com.au";
        boolean validUrl = UrlUtil.isValidUrl(originalUrl);
        System.out.println("validUrl = " + validUrl);
    }
}
