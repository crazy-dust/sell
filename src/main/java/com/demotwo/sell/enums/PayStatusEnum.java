package com.demotwo.sell.enums;

import lombok.Getter;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 0:31
 * @Desc:
 */
@Getter
public enum  PayStatusEnum implements StatusEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    PAY_STATE_ERROR(3, "支付状态错误")
    ;

    private Integer state;

    private String stateInfo;

    PayStatusEnum(Integer state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
}