package com.demotwo.sell.dao;

import com.demotwo.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 0:20
 * @Desc:
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /**
     * 按照买家的openId来查订单
     * 如果不传入pageable, 那么就是把某个人的所有订单都查出来, 这样订单量太大
     * 如果传入pageable, 就支持分页了
     * @param buyerOpenId
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenId(String buyerOpenId, Pageable pageable);
}
