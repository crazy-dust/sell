package com.demotwo.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: liudongyang
 * @Date: 2018/9/17 19:07
 * @Desc:
 */
@Data
public class OrderForm {
    /**
     * 买家姓名
     */
    @NotEmpty(message = "买家姓名必填")
    private String name;

    /**
     * 买家电话
     */
    @NotEmpty(message = "买家电话必填")
    private String phone;

    /**
     * 买家地址
     */
    @NotEmpty(message = "买家地址必填")
    private String address;

    /**
     * 买家OpenId
     */
    @NotEmpty(message = "买家OpenId必填")
    private String openId;

    /**
     * 购物车
     */
    @NotEmpty(message = "购物车信息必填")
    private String items;
}

