package com.demotwo.sell.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 17:41
 * @Desc: 商品
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = -294567798935243767L; // because ： reids缓存

    /** 商品ID */
    @Id
    private String productId;

    /** 商品名称 */
    private String productName;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 商品库存 */
    private Integer productStock;

    /** 商品简介 */
    private String productDescription;

    /** 商品小图 */
    private String productIcon;

    /** 类目编号 */
    private Integer categoryType;

    /** 商品状态, 0正常 1下架 */
    private Integer productStatus;

    private Date createTime;

    private Date updateTime;
}