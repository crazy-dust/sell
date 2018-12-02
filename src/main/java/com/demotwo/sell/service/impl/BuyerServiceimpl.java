package com.demotwo.sell.service.impl;

import com.demotwo.sell.dto.OrderMasterDTO;
import com.demotwo.sell.entity.OrderMaster;
import com.demotwo.sell.enums.OrderStatusEnum;
import com.demotwo.sell.exception.SellException;
import com.demotwo.sell.service.BuyerService;
import com.demotwo.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: liudongyang
 * @Date: 2018/9/18 0:53
 * @Desc:
 */
@Service
@Slf4j
public class BuyerServiceimpl implements BuyerService {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 根据订单号查找订单，先校验是否本人操作
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderMasterDTO findOrderOne(String openid, String orderId) {
        return checkOrderBuyerOpenId(openid, orderId);
    }

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderMasterDTO cancelOrder(String openid, String orderId) {
        OrderMasterDTO orderMasterDTO = checkOrderBuyerOpenId(openid, orderId);
        if(orderMasterDTO == null) {
            log.error("【订单取消】, 当前订单非法操作, buyerOpenId = {}", orderMasterDTO.getBuyerOpenId());
            throw new SellException(OrderStatusEnum.ILLEGALOPERATIONBYTHECURRENTUSER);
        }

        OrderMasterDTO orderMasterDTO2 = orderMasterService.cancel(orderMasterDTO);
        return orderMasterDTO2;
    }

    /**
     * 校验订单呢是否本人操作
     * @param openid
     * @param orderId
     * @return
     */
    public OrderMasterDTO checkOrderBuyerOpenId(String openid, String orderId) {
        // 安全考虑，传进来的openid要和当前操作的订单买主是否一个人
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);

        if(orderMasterDTO == null) {
            return null;
        }

        if(!orderMasterDTO.getBuyerOpenId().equals(openid)) {
            log.error("【订单错误】, 当前订单非法操作, buyerOpenId = {}", orderMasterDTO.getBuyerOpenId());
            throw new SellException(OrderStatusEnum.ILLEGALOPERATIONBYTHECURRENTUSER);
        }

        return orderMasterDTO;
    }
}

