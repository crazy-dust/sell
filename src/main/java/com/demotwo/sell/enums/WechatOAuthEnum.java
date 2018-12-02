package com.demotwo.sell.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: liudongyang
 * @Date: 2018/9/23 23:36
 * @Desc:
 */
@Getter
public enum WechatOAuthEnum {

    WECHAT_ERROR(0, "微信授权有误");

    private int state;
    private String stateInfo;

    WechatOAuthEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
}