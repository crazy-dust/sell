package com.demotwo.sell.util;

import com.demotwo.sell.entity.OrderDetail;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.text.html.parser.Entity;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author: liudongyang
 * @Date: 2018/9/17 21:41
 * @Desc:
 */
public class Test {

    public static void main(String[] args) {
        /*try {
            List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
            String str = "[{\"productId\": \"123456\", \"productQuantity\": \"20\"}]";
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, OrderDetail.class);
            orderDetailList = objectMapper.readValue(str, javaType);
            System.out.println(orderDetailList);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
//        iteratorMap();
    }


    public static void iteratorMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");

        // 方式一：
        for (Map.Entry<String, String> stringEntry : map.entrySet()) {
            System.out.println(stringEntry.getKey() + stringEntry.getValue());
        }

        // *** 方式二：此种方式性能好
        //遍历key键
        for (String keyStr : map.keySet()) {
            System.out.println(keyStr);
        }

        //遍历value值
        for (String valueStr : map.values()) {
            System.out.println(valueStr);
        }

        // *** 方式三：使用iterator, 性能不错而且可以解除迭代器删除集合中的元素
        Iterator<Map.Entry<String, String>>  iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, String> entity = iterator.next();
            System.out.println(entity.getKey());     //获取key
            System.out.println(entity.getValue());   //获取value
        }

        //方式四：通过键找值遍历
        for (String keyStr : map.keySet()) {
            System.out.println(keyStr);
            System.out.println(map.get(keyStr));
        }
    }
}
