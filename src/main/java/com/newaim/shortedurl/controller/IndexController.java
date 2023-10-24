package com.newaim.shortedurl.controller;

import com.joseph.core.util.StrUtil;
import com.newaim.shortedurl.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Joseph.Liu
 */
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UrlService urlService;

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

    @GetMapping("/{shortUrl}")
    public String redirect(@PathVariable("shortUrl") String shortUrl){
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        if(originalUrl != null){
            urlService.updateUrlViews(shortUrl);
            return "redirect:"+originalUrl;
        }
        return "redirect:"+ StrUtil.EMPTY;
    }
}
