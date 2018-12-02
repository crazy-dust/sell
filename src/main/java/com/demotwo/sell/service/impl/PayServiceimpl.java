package com.demotwo.sell.service.impl;

import com.demotwo.sell.dto.OrderMasterDTO;
import com.demotwo.sell.enums.OrderStatusEnum;
import com.demotwo.sell.exception.SellException;
import com.demotwo.sell.service.OrderMasterService;
import com.demotwo.sell.service.PayService;
import com.demotwo.sell.util.JsonUtil;
import com.demotwo.sell.util.MathUtils;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: liudongyang
 * @Date: 2018/9/24 15:57
 * @Desc:
 */
@Service
@Slf4j
public class PayServiceimpl implements PayService {

    public static final String ORDER_NAME = "微信点餐系统订单";

    @Autowired
    private BestPayService bestPayService;

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * sdk支付订单
     * @param orderMasterDTO
     * @return
     */
    @Override
    public PayResponse create(OrderMasterDTO orderMasterDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderMasterDTO.getOrderId());
        payRequest.setOrderAmount(orderMasterDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderMasterDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】 request = {}", JsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);   //生成预付单，返回的相应体里面包含prePayId
        log.info("【微信支付】 response = {}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    /**
     * 异步通知
     * @param notifyData
     * @return
     */
    @Override
    public PayResponse notify(String notifyData) {
        //1. 验证签名   sdk解决
        //2. 支付的状态
        //3. 支付金额
        //4. 支付人(下单人 == 支付人)  看业务情况，因为有的时候可以是他人代付
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);   //调用sdk的异步通知

        // 修改订单状态, 首先需要获取到该订单
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(payResponse.getOrderId());
        if(orderMasterDTO == null) {
            log.error("【微信支付异步通知】 订单不存在 orderMasterDTO = {}", orderMasterDTO);
            throw new SellException(OrderStatusEnum.ORDER_NOT_EXIST);
        }

        //判断金额是否一致
        if(!MathUtils.compare(payResponse.getOrderAmount(), orderMasterDTO.getOrderAmount().doubleValue())) {
            log.error("【微信支付异步通知】 系统订单金额和预付单金额不一致, orderId = {}, payResponse = {}, orderMasterDTO = {}", orderMasterDTO.getOrderId(), payResponse, orderMasterDTO);
            throw new SellException(OrderStatusEnum.WX_PREPAID_ORDER_AMOUNT_ERROR);
        }

        //修改支付的状态
        orderMasterService.paid(orderMasterDTO);

        return payResponse;
    }

    /**
     * 退款
     * @param orderMasterDTO
     */
    @Override
    public RefundResponse refund(OrderMasterDTO orderMasterDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderMasterDTO.getOrderId());
        refundRequest.setOrderAmount(orderMasterDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        return refundResponse;
    }
}

