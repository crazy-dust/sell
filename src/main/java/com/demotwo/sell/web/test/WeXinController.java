package com.demotwo.sell.web.test;

import com.demotwo.sell.dto.UserAccessToken;
import com.demotwo.sell.dto.WechatUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 通过RestTemplate原生的方式获取微信授权
 * @Author: liudongyang
 * @Date: 2018/9/23 21:45
 * @Desc:
 */
@RestController
@RequestMapping("/wechatlogin")
@Slf4j
public class WeXinController {

    @GetMapping("/logincheck")
    public WechatUser authorize(@RequestParam("code") String code) {
        String appid = "wx84a99cc4c9a1664f";
        String appSecret = "416ac7adcc10e66e17dc85f6fe523fec";
        log.info("【获取code】 code = {}", code);

        RestTemplate template = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + appSecret +"&code=" + code +"&grant_type=authorization_code";
        UserAccessToken userAccessToken = template.getForObject(url, UserAccessToken.class);
        String accessToken = userAccessToken.getAccessToken();
        String openId = userAccessToken.getOpenId();

        String personInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken +"&openid=" + openId +"&lang=zh_CN";
        WechatUser wechatUser = template.getForObject(personInfoUrl, WechatUser.class);
        log.info(wechatUser.toString());
        return wechatUser;
    }

}

