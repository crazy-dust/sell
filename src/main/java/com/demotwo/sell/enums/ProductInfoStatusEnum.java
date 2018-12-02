package com.demotwo.sell.enums;

import lombok.Getter;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 20:03
 * @Desc:
 */
@Getter
public enum ProductInfoStatusEnum {
    UP(0, "上架"),
    DOWN(1, "下架"),

    NO_EXISTS_PRODUCT(2, "找不到商品"),
    SUCCESS(10, "成功"),
    UPDATE_ERROR(11, "修改商品信息失败"),
    PRODUCTFORM_CONVERTER_ERROR(12, "表单对象转换错误"),
    FORM_IMAGES_NULL(13, "表单中的缩略图不能为空")
    ;

    private int state;

    private String stateInfo;

    ProductInfoStatusEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
}
