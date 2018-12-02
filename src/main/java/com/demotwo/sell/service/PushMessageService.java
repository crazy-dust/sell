package com.demotwo.sell.service;

import com.demotwo.sell.dto.OrderMasterDTO;

/**
 * @Author: liudongyang
 * @Date: 2018/9/29 17:28
 * @Desc:
 */
public interface PushMessageService {

    //推送模板消息
    void orderStatus(OrderMasterDTO orderMasterDTO);

}
