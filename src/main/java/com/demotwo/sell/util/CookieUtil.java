package com.demotwo.sell.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liudongyang
 * @Date: 2018/9/28 20:34
 * @Desc:
 */
public class CookieUtil {

    /**
     * 设置一个cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response,
                                 String name,
                                 String value,
                                 int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest request,
                                   String name) {
        Map<String, Cookie> map = readCookieMap(request.getCookies());
        Cookie cookie = null;
        if(map.containsKey(name)) {
            cookie = map.get(name);
        }
        return cookie;
    }

    public static Map<String, Cookie> readCookieMap(Cookie[] cookies) {
        Map<String, Cookie> map = new HashMap<String, Cookie>();
        for (Cookie cookie : cookies) {
            map.put(cookie.getName(), cookie);
        }
        return map;
    }

}