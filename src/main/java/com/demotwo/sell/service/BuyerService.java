package com.demotwo.sell.service;

import com.demotwo.sell.dto.OrderMasterDTO;

/**
 * @Author: liudongyang
 * @Date: 2018/9/18 0:51
 * @Desc:
 */
public interface BuyerService {

    // 查询一个订单
    OrderMasterDTO findOrderOne(String openid, String orderId);

    // 取消订单
    OrderMasterDTO cancelOrder(String openid, String orderId);
}