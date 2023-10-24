package com.newaim.shortedurl.util;

import java.util.regex.Pattern;

/**
 * @author Joseph.Liu
 */
public class UrlUtil {

    private static final Pattern URL_PATTERN = Pattern.compile("^(((ht|f)tps?):\\/\\/)?[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$");

    private static final Pattern URL_PATTERN2 = Pattern.compile("^(https?://)?[\\w\\.-]+(?:\\.\\w+)+(/([\\w\\-\\.~]|[:///?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=])*)?$");

    public static boolean isValidUrl(String url){
        return URL_PATTERN.matcher(url).matches();
    }
}
