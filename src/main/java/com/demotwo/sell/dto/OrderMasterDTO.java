package com.demotwo.sell.dto;

import com.demotwo.sell.entity.OrderDetail;
import com.demotwo.sell.enums.OrderStatusEnum;
import com.demotwo.sell.enums.PayStatusEnum;
import com.demotwo.sell.util.Date2LongSerializer;
import com.demotwo.sell.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 17:40
 * @Desc:
 */
@Data
public class OrderMasterDTO {

    /** 订单ID */
    private String orderId;

    /** 订单人名字 */
    private String buyerName;

    /** 订单人电话 */
    private String buyerPhone;

    /** 订单人地址 */
    private String buyerAddress;

    /** 订单人微信OpenId */
    private String buyerOpenId;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态, 默认0表示新下单 */
    private Integer orderStatus;

    /** 订单支付状态, 默认0未支付 */
    private Integer payStatus;

    /** 创建时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /** 订单详情列表 */
    private List<OrderDetail> orderDetailList;

    // 获取订单状态
    /*NEW(0, "新下单"),
    FINSHED(1, "完结"),
    CANCEL(2, "已取消"),*/
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getEnum(orderStatus, OrderStatusEnum.class);
    }

    // 获取支付状态
    /*WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    PAY_STATE_ERROR(3, "支付状态错误")*/
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getEnum(payStatus, PayStatusEnum.class);
    }

}

