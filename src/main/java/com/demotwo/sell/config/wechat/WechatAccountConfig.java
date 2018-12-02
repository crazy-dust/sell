package com.demotwo.sell.config.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 扫描yml文件的填入值
 * @Author: liudongyang
 * @Date: 2018/9/23 23:14
 * @Desc:
 */
@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /**
     * 微信公众平台id
     */
    private String appid;

    /**
     * 微信公众平台秘钥
     */
    private String appsecret;

    /**
     * 微信开放平台id
     */
    private String openAppid;

    /**
     * 微信开放平台秘钥
     */
    private String openSecret;

    /**
     * mchId: 测试商户Id
     */
    private String mchId;

    /**
     * mchkey: 测试商户秘钥
     */
    private String mchKey;

    /**
     * mchPath: 测试商户证书路径
     */
    private String mchPath;

    /**
     * 异步通知URL
     */
    private String notifyUrl;

    /**
     * 模板通知
     */
    private Map<String, String> templateId;
}