package com.demotwo.sell.util;

/**
 * @Author: liudongyang
 * @Date: 2018/9/25 0:05
 * @Desc:
 */
public class MathUtils {

    public static final double LIMITEDAMOUNT = 0.01;

    public static boolean compare(Double d1, Double d2) {
        Double d = Math.abs(d1 - d2);
        if(d < LIMITEDAMOUNT) {
            return true;
        } else {
            return false;
        }
    }

}