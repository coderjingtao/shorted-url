package com.newaim.shortedurl;

import com.joseph.core.util.IdUtil;
import com.newaim.shortedurl.model.UrlMapModel;
import com.newaim.shortedurl.mapper.UrlMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Joseph.Liu
 */
@SpringBootTest
public class MyBatisTest {

    @Autowired
    UrlMapper urlMapper;

    @Test
    public void testQueryAllUrls(){
        List<UrlMapModel> allUrls = urlMapper.getAllUrlMaps();
        System.out.println(allUrls.size());
    }

    @Test
    public void testSaveUrl(){
        UrlMapModel model = new UrlMapModel();
        model.setId(IdUtil.fastSimpleUUID());
        model.setOriginalUrl("www.google.com.au");
        model.setShortUrl("2G8Bwz");
        int i = urlMapper.saveUrl(model);
        System.out.println("i = " + i);
    }

    @Test
    public void testQueryLongUrlByShortUrl(){
        String originalUrl = urlMapper.getOriginalUrl("2G8Bwz");
        System.out.println("originalUrl = " + originalUrl);
    }

    @Test
    public void testUpdateViews(){
        int i = urlMapper.updateUrlViews("2G8Bwz");
        System.out.println("i = " + i);
    }
}
