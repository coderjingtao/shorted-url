package com.newaim.shortedurl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joseph.bloomfilter.BloomFilter;
import com.joseph.bloomfilter.BloomFilterUtil;
import com.joseph.core.util.IdUtil;
import com.joseph.core.util.StrUtil;
import com.newaim.shortedurl.mapper.UrlMapper;
import com.newaim.shortedurl.model.UrlMapModel;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Joseph.Liu
 */
@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService{

    private final StringRedisTemplate stringRedisTemplate;
    private final UrlMapper urlMapper;

    private static final long TIMEOUT = 10;
    private static final TimeUnit TIMEUNIT = TimeUnit.MINUTES;
    private static final String DUP_SUFFIX = "#";
    private static final BloomFilter BLOOM_FILTER = BloomFilterUtil.createBitMap(10);

    @Override
    public String getOriginalUrl(String shortUrl) {
        String originalUrl = stringRedisTemplate.opsForValue().get(shortUrl);
        if(originalUrl != null){
            stringRedisTemplate.expire(shortUrl,TIMEOUT,TIMEUNIT);
            return originalUrl;
        }
        originalUrl = urlMapper.getOriginalUrl(shortUrl);
        if(originalUrl != null){
            stringRedisTemplate.opsForValue().set(shortUrl,originalUrl,TIMEOUT,TIMEUNIT);
        }
        return originalUrl;
    }

    @Override
    public String saveUrlMap(String shortUrl, String longUrl, String originalUrl) {
        //short url exists in BloomFilter
        if(BLOOM_FILTER.contains(shortUrl)){
            //find if the short url has cache in redis
            String redisOriginalUrl = stringRedisTemplate.opsForValue().get(shortUrl);
            if(redisOriginalUrl != null && StrUtil.equals(redisOriginalUrl,originalUrl)){
                //the short url has cache in redis, extend its time
                stringRedisTemplate.expire(shortUrl,TIMEOUT,TIMEUNIT);
                return shortUrl;
            }
            //the short url has no cache in redis, rehash the long url with a suffix
            longUrl += DUP_SUFFIX;
            shortUrl = saveUrlMap(StrUtil.hashToBase62(longUrl),longUrl,originalUrl);
        }else{
            //short url doesn't exist in BloomFilter
            try{
                UrlMapModel urlMapModel = new UrlMapModel();
                urlMapModel.setId(IdUtil.fastSimpleUUID());
                urlMapModel.setShortUrl(shortUrl);
                urlMapModel.setOriginalUrl(originalUrl);

                urlMapper.saveUrl(urlMapModel);
                BLOOM_FILTER.add(shortUrl);
                stringRedisTemplate.opsForValue().set(shortUrl,originalUrl,TIMEOUT,TIMEUNIT);
            } catch (Exception ex){
                if(ex instanceof DuplicateKeyException){
                    //BloomFilter misjudged the shortUrl, need to re-hash the long url
                    longUrl += DUP_SUFFIX;
                    shortUrl = saveUrlMap(StrUtil.hashToBase62(longUrl),longUrl,originalUrl);
                }else{
                    throw ex;
                }
            }
        }
        return shortUrl;
    }

    @Override
    public String getAllUrlMaps() {
        List<UrlMapModel> allUrlMaps = urlMapper.getAllUrlMaps();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.writeValueAsString(allUrlMaps);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
            return StrUtil.EMPTY_JSON;
        }
    }

    @Override
    public void updateUrlViews(String shortUrl) {
        urlMapper.updateUrlViews(shortUrl);
    }
}
