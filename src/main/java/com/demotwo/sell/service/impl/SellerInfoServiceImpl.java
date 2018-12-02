package com.demotwo.sell.service.impl;

import com.demotwo.sell.dao.SellerInfoRepository;
import com.demotwo.sell.entity.SellerInfo;
import com.demotwo.sell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: liudongyang
 * @Date: 2018/9/28 16:19
 * @Desc:
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findByOpenId(String opendId) {
       return sellerInfoRepository.findByOpenId(opendId);
    }

    @Override
    public SellerInfo save(SellerInfo sellerInfo) {
        return sellerInfoRepository.save(sellerInfo);
    }


}