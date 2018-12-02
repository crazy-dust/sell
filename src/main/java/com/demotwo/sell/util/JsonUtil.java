package com.demotwo.sell.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: liudongyang
 * @Date: 2018/9/24 16:31
 * @Desc:
 */
public class JsonUtil {

    public static String toJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String str  = null;
        try {
            str = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }

}