package com.demotwo.sell.service;

import com.demotwo.sell.dto.OrderMasterDTO;
import com.demotwo.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 16:22
 * @Desc:
 */
public interface OrderMasterService {

    /** 创建订单 */
    OrderMasterDTO create(OrderMasterDTO orderMasterDTO);

    /** 查询单个订单 */
    OrderMasterDTO findOne(String orderId);

    /** 查询订单列表 */
    Page<OrderMasterDTO> findList(String buyerOpenId, Pageable pageable);

    /** 取消订单 */
    OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO);

    /** 完结订单 */
    OrderMasterDTO finsh(OrderMasterDTO orderMasterDTO);

    /** 支付订单 */
    OrderMasterDTO paid(OrderMasterDTO orderMasterDTO);

    /**
     * 查询所有的订单信息（支持分页）
     * @param pageable
     * @return
     */
    Page<OrderMasterDTO> findByList(Pageable pageable);
}