package com.demotwo.sell.service;

import com.demotwo.sell.entity.SellerInfo;

/**
 * @Author: liudongyang
 * @Date: 2018/9/28 16:18
 * @Desc:
 */
public interface SellerInfoService {

    SellerInfo findByOpenId(String opendId);

    SellerInfo save(SellerInfo sellerInfo);
}