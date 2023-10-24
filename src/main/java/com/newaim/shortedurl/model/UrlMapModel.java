package com.newaim.shortedurl.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Joseph.Liu
 */
@Data
@NoArgsConstructor
public class UrlMapModel {
    private String id;
    private String originalUrl;
    private String shortUrl;
    private Long views;
    private Date createTime;
}
