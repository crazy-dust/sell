package com.demotwo.sell.enums;

import lombok.Getter;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 0:22
 * @Desc:
 */
@Getter
public enum OrderStatusEnum implements StatusEnum {

    SUCCESS(100, "成功"),
    NEW(0, "新下单"),
    FINSHED(1, "完结"),
    CANCEL(2, "已取消"),

    ORDER_STATE_ERROR(5, "订单状态错误"),
    ORDER_NOT_EXIST(3, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(4, "订单详情不存在"),
    ORDER_UPDATE_FAIL(6, "更新失败"),
    ORDER_DETAIL_EMPTY(7, "订单明细清空"),
    ORDER_PARAMETER_ERROR(8, "订单参数有误"),
    CART_CANNOTBEEMPTY(9, "购物车不能为空"),
    ILLEGALOPERATIONBYTHECURRENTUSER(10, "当前用户非法操作"),
    WX_PREPAID_ORDER_AMOUNT_ERROR(11, "系统订单金额和预付单金额不一致")
    ;

    private Integer state;

    private String stateInfo;

    OrderStatusEnum(Integer state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
}
