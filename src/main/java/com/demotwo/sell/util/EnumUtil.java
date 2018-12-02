package com.demotwo.sell.util;

import com.demotwo.sell.enums.StatusEnum;

/**
 * @Author: liudongyang
 * @Date: 2018/9/25 21:51
 * @Desc:
 */
public class EnumUtil {

    public static <T extends StatusEnum> T getEnum(Integer code, Class<T> statusEnumClass) {
        for (T t : statusEnumClass.getEnumConstants()) {
            if(code.equals(t.getState())) {
                return t;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            Class c = Class.forName("com.demotwo.sell.entity.OrderDetail");
            Object o = c.newInstance();
            System.out.println(c == o.getClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
