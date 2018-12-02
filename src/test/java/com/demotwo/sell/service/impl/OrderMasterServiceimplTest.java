package com.demotwo.sell.service.impl;

import com.demotwo.sell.dto.OrderMasterDTO;
import com.demotwo.sell.entity.OrderDetail;
import com.demotwo.sell.entity.ProductInfo;
import com.demotwo.sell.enums.OrderStatusEnum;
import com.demotwo.sell.enums.PayStatusEnum;
import com.demotwo.sell.service.OrderMasterService;
import com.demotwo.sell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 22:18
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceimplTest {

    public static final String orderId = "15397130146608979721";

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    @Repeat(value = 20)
    public void create() {
        // 购物车 订单详情项
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        ProductInfo productInfo = productInfoService.findOne("123456");
        orderDetail.setProductId(productInfo.getProductId());
        orderDetail.setProductPrice(productInfo.getProductPrice());
        orderDetail.setProductQuantity(10);
        orderDetailList.add(orderDetail);

        OrderDetail orderDetail2 = new OrderDetail();
        ProductInfo productInfo2 = productInfoService.findOne("1234567");
        orderDetail2.setProductId(productInfo2.getProductId());
        orderDetail2.setProductPrice(productInfo2.getProductPrice());
        orderDetail2.setProductQuantity(20);
        orderDetailList.add(orderDetail2);

        // 主订单
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        orderMasterDTO.setBuyerAddress("测试地址");
        orderMasterDTO.setBuyerOpenId("110110");
        orderMasterDTO.setBuyerPhone("123456789");
        orderMasterDTO.setBuyerName("测试名字");
        orderMasterDTO.setOrderDetailList(orderDetailList);

        OrderMasterDTO orderMasterDTO1 = orderMasterService.create(orderMasterDTO);
        System.out.println(orderMasterDTO1);
    }

    @Test
    public void findOne() {
        String orderId = "15397070648001910698";
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
        log.info("【查询一个订单】 result = {}", orderMasterDTO);
    }

    @Test
    public void findList() {
        String buyerOpenId = "110110";
        PageRequest pageRequest = new PageRequest(0, 3);
        Page<OrderMasterDTO> orderMasterDTOPage = orderMasterService.findList(buyerOpenId, pageRequest);
        log.info("【分页查询】 result = {}", orderMasterDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
        OrderMasterDTO masterDTO = orderMasterService.cancel(orderMasterDTO);
        Assert.assertEquals(OrderStatusEnum.FINSHED.getState(), masterDTO.getOrderStatus());
    }

    @Test
    public void finsh() {
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
        OrderMasterDTO masterDTO = orderMasterService.finsh(orderMasterDTO);
        Assert.assertEquals(OrderStatusEnum.FINSHED.getState(), masterDTO.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
        OrderMasterDTO masterDTO = orderMasterService.paid(orderMasterDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getState(), masterDTO.getPayStatus());
    }

    @Test
    public void findByList() {
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<OrderMasterDTO> orderMasterDTOPage = orderMasterService.findByList(pageRequest);
        Assert.assertNotEquals(0, orderMasterDTOPage.getTotalElements());
    }


}