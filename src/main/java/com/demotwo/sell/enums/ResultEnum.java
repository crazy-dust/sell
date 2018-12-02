package com.demotwo.sell.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 18:10
 * @Desc:
 */
@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXISTS(10, "商品不存在"), INVENTORYSHORTAGE(11, "库存不足"), PARAMETER_ERROR(12, "参数异常");

    private Integer state;

    private String stateInfo;

    ResultEnum(Integer state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
}