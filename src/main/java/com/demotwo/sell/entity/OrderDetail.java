package com.demotwo.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 0:39
 * @Desc:
 */
@Entity
@Data
@DynamicUpdate
public class OrderDetail {

    /** 订单详情ID */
    @Id
    private String detailId;

    /** 主订单ID */
    private String orderId;

    /** 商品ID, 一个订单详情一个商品 */
    private String productId;

    /** 商品名称 */
    private String productName;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 商品数量 */
    private Integer productQuantity;

    /** 商品小图 */
    private String productIcon;

    /** 创建时间 */
    private Date createTime;

    public OrderDetail() {
    }
}

