package com.demotwo.sell.web.wechat;

import com.demotwo.sell.enums.WechatOAuthEnum;
import com.demotwo.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * 通过sdk的方式获取微信授权、扫码登录
 * @Author: liudongyang
 * @Date: 2018/9/23 23:07
 * @Desc:
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    /**
     * 买家端授权登录
     * 微信公众平台
     *
     * 构造网页授权url, 然后构成超链接让用户点击, 这里的redirect就已经进入了超链接.
     * 然后会被微信用我们自定义的回调链接到页面，传入code和state。
     * 这里的returnUrl只是state传参而已！
     * 自定义的连接get请求进入controller的方法(returnUrl)---构建授权URL redirect到微信服务器(returnUrl)---回调到自定义的超连接(returnUrl)
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        //1. 配置 WechatPayConfig类, 配置beanwxMpService和WechatAccountConfig
        //2. 调用方法

        String callbackUrl = "/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(callbackUrl, WxConsts.OAUTH2_SCOPE_USER_INFO,
                             URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;
    }

    /**
     * 买家端授权登录
     *
     * 微信公众平台
     *
     * 获得access token
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.info("【微信授权有误】 wxMpOAuth2AccessToken = {}", wxMpOAuth2AccessToken);
            throw new SellException(WechatOAuthEnum.WECHAT_ERROR.getState(), e.getError().getErrorMsg());
        }
        return "redirect:" + returnUrl + "?" + wxMpOAuth2AccessToken.getOpenId();
    }

    /**
     * 微信开放平台,这里为了实现卖家端扫码登录功能
     *
     * 构造网页授权url, 然后构成超链接让用户点击, 这里的redirect就已经进入了超链接.
     * 然后会被微信用我们自定义的回调链接到页面，传入code和state。
     * 这里的returnUrl只是state传参而已！
     * 自定义的连接get请求进入controller的方法(returnUrl)---构建授权URL redirect到微信服务器(returnUrl)---回调到自定义的超连接(returnUrl)
     */
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
        //1. 配置 WechatPayConfig类, 配置beanwxMpService和WechatAccountConfig
        //2. 调用方法

        String callbackUrl = "/sell/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(callbackUrl, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN,
                URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;
    }

    /**
     * 微信开放平台,这里为了实现卖家端扫码登录功能
     *
     * 获得access token
     */
    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.info("【微信授权有误】 wxMpOAuth2AccessToken = {}", wxMpOAuth2AccessToken);
            throw new SellException(WechatOAuthEnum.WECHAT_ERROR.getState(), e.getError().getErrorMsg());
        }
        return "redirect:" + returnUrl + "?" + wxMpOAuth2AccessToken.getOpenId();
    }
}

