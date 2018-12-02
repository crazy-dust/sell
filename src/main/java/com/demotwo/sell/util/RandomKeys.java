package com.demotwo.sell.util;
import java.util.Random;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 18:26
 * @Desc:
 */
public class RandomKeys {

    /**
     * 生成随机主键(时间戳 + 6位数字)
     * @return
     */
    public static synchronized String generateRandomKeys() {
        Random random = new Random();
        Integer number = random.nextInt(9000000) + 1000000;

        return System.currentTimeMillis() + String.valueOf(number);
    }

    public static void main(String[] args) {
        String str = generateRandomKeys();
        System.out.println(str);
        System.out.println("长度" + str.length());

        long l = 123234334L;
        System.out.println(l + "ceshi");
    }
}


