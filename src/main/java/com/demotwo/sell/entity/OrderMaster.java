package com.demotwo.sell.entity;

import com.demotwo.sell.enums.OrderStatusEnum;
import com.demotwo.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 0:13
 * @Desc: 订单主表
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /** 订单ID */
    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getState();

    /** 订单支付状态, 默认0未支付 */
    private Integer payStatus = PayStatusEnum.WAIT.getState();

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}