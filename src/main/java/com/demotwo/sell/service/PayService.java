package com.demotwo.sell.service;

import com.demotwo.sell.dto.OrderMasterDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * @Author: liudongyang
 * @Date: 2018/9/24 15:56
 * @Desc:
 */
public interface PayService {

     PayResponse create(OrderMasterDTO orderMasterDTO);

     PayResponse notify(String notifyData);

     RefundResponse refund(OrderMasterDTO orderMasterDTO);
}
