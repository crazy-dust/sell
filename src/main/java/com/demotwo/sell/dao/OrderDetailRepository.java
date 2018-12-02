package com.demotwo.sell.dao;

import com.demotwo.sell.entity.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 0:49
 * @Desc:
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /**
     * 根据主订单ID来查多个订单详情(一个订单包含多个订单详情)
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);

}
