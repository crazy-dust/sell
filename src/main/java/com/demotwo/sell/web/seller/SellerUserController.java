package com.demotwo.sell.web.seller;

import com.demotwo.sell.config.web.ProjectConfig;
import com.demotwo.sell.dto.CookieConstant;
import com.demotwo.sell.dto.RedisConstant;
import com.demotwo.sell.entity.SellerInfo;
import com.demotwo.sell.service.SellerInfoService;
import com.demotwo.sell.util.CookieUtil;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liudongyang
 * @Date: 2018/9/28 19:44
 * @Desc:
 */
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerInfoService sellerInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProjectConfig projectConfig;

    @GetMapping("/login")
        public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {

        // 1. 首先匹配数据库的数据
        SellerInfo sellerInfo = sellerInfoService.findByOpenId(openid);
        if(sellerInfo == null) {
            log.error("【登录错误】 找不到该用户 openid = {}", openid);
            map.put("msg", "找不到该用户");
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        // 2. token 存入redis

        //设置UUID
        String token = UUID.randomUUID().toString();
        //设置cookie和redis的过期时间
        Integer expire = RedisConstant.EXPIRE;
        /*
            设置redis:
            name: 格式化name(token_UUID)
            value: openid
            过期时间: 7200
         */
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.FORMAT_TOKEN, token), openid, expire, TimeUnit.SECONDS);

        //3. token 存入 cookie
        /*
            name: "token"
            value: UUID
            过期时间: 7200
            也就是说拿的时候是拿cookie(name + value)拼接的字符串作为拿取redis的key
         */
        CookieUtil.setCookie(response, CookieConstant.TOKEN, token, expire);

        return new ModelAndView("redirect:" + projectConfig.getSell() + "/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {

        // 1. 获取cookie
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if(cookie != null) {
            // 2. 删除redis中的token
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.FORMAT_TOKEN, cookie.getValue()));
            // 3. 删除cookie中的token
            CookieUtil.setCookie(response, CookieConstant.TOKEN, null, 0);
        }
        map.put("msg", "登出成功");
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
