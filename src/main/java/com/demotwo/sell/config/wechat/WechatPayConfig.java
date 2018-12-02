package com.demotwo.sell.config.wechat;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 微信支付
 * @Author: liudongyang
 * @Date: 2018/9/24 16:06
 * @Desc:
 */
@Component
public class WechatPayConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public BestPayServiceImpl createBestPayServiceImpl() {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(createWxPayH5Config());
        return bestPayService;
    }

    @Bean
    public WxPayH5Config createWxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAccountConfig.getAppid());
        wxPayH5Config.setAppSecret(wxPayH5Config.getAppSecret());
        wxPayH5Config.setMchId(wxPayH5Config.getMchId());
        wxPayH5Config.setMchKey(wxPayH5Config.getMchKey());
        wxPayH5Config.setKeyPath(wechatAccountConfig.getMchPath());
        wxPayH5Config.setNotifyUrl(wechatAccountConfig.getNotifyUrl());    //微信异步通知!
        return wxPayH5Config;
    }

}