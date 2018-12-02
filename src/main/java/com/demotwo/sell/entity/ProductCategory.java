package com.demotwo.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: liudongyang
 * @Date: 2018/9/14 23:56
 * @Desc: 商品类别(类目)
 */
@Entity
@Table
/** @Table标识主要用于和表名不一致的时候用name来指定表名的,
 * 本例中和表名"product_category"底层利用驼峰标识自动处理 */
@Data
@DynamicUpdate
public class ProductCategory {

    @GeneratedValue
    @Id
    /** 类别ID(自增) */
    private Integer categoryId;

    /** 类别名字 */
    private String categoryName;

    /** 类目编号 */
    private Integer categoryType;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}