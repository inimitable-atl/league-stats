package com.sethtomy.util;

import java.util.Map;

public class HttpUtil {

    public static String addQueryParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        params.forEach((key, value) -> sb.append(String.format("%s=%s&", key, value)));
        return sb.toString();
    }

}
