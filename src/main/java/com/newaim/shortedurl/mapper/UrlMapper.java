package com.newaim.shortedurl.mapper;

import com.newaim.shortedurl.model.UrlMapModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Joseph.Liu
 */
@Repository
@Mapper
public interface UrlMapper {

    @Select("select original_url from url_map where short_url = #{shortUrl} ")
    String getOriginalUrl(String shortUrl);

    @Insert("insert into url_map (id, original_url, short_url) values (#{id}, #{originalUrl}, #{shortUrl}) ")
    int saveUrl(UrlMapModel urlMapModel);

    @Update("update url_map set views = views + 1 where short_url = #{shortUrl}")
    int updateUrlViews(String shortUrl);

    @Select("select * from url_map")
    List<UrlMapModel> getAllUrlMaps();
}
