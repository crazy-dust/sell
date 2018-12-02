package com.demotwo.sell.aspect;

import com.demotwo.sell.dto.CookieConstant;
import com.demotwo.sell.dto.RedisConstant;
import com.demotwo.sell.exception.SellVerifyException;
import com.demotwo.sell.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: liudongyang
 * @Date: 2018/9/29 1:10
 * @Desc:
 */
@Aspect
@Component
@Slf4j
public class SellerUserAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 定义一个切入点集合:
     * 拦截后台管理系统的对于增删改操作的controller,
     * 让所有用户必须经过微信扫码登录才能进行增删改的操作
     * 这里异常的拦截使用redirect 百度代替
     */
    @Pointcut("execution(public * com.demotwo.sell.web.seller.*.*(..))" +
    "&& !execution(public * com.demotwo.sell.web.seller.SellerUserController.*(..))")
    public void verify() {}

    @Before("verify()")
    public void doVerify() {
        // 1. 查询cookie
        // 1.1 拿到Request(和response)
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 1.2 查询cookie
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if(cookie == null) {
            log.warn("【登录验证】 找不到cookie");
            throw new SellVerifyException();
        }
        // 2. redis查询token
        String token  = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.FORMAT_TOKEN, cookie.getValue()));
        if(token == null) {
            log.warn("【登录验证】 找不到token");
            throw new SellVerifyException();
        }
    }

}