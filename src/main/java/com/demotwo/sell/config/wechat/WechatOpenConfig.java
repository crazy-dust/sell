package com.demotwo.sell.config.wechat;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 微信扫码登录
 * @Author: liudongyang
 * @Date: 2018/9/28 16:46
 * @Desc:
 */
@Component
public class WechatOpenConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxMpService wxOpenService() {
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(openWxMpConfigStorage());
        return wxOpenService;
    }

    @Bean
    public WxMpConfigStorage openWxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(wechatAccountConfig.getOpenAppid());
        wxMpConfigStorage.setSecret(wechatAccountConfig.getOpenSecret());
        return wxMpConfigStorage;
    }

}