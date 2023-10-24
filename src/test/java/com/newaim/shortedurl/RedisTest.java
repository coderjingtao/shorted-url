package com.newaim.shortedurl;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author Joseph.Liu
 */
@SpringBootTest
public class RedisTest {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testAddToRedis(){
        String shortUrl = "2G8Bwz";
        String originalUrl = "www.google.com.au";
        stringRedisTemplate.opsForValue().set(shortUrl,originalUrl,10, TimeUnit.MINUTES);
    }

    @Test
    public void testGetFromRedis(){
        String shortUrl = "2G8Bwz";
        String originalUrl = stringRedisTemplate.opsForValue().get(shortUrl);
        System.out.println("originalUrl = " + originalUrl);
    }
}
