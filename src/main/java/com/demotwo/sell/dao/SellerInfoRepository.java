package com.demotwo.sell.dao;

import com.demotwo.sell.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: liudongyang
 * @Date: 2018/9/28 16:12
 * @Desc:
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    SellerInfo findByOpenId(String openId);

}