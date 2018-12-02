package com.demotwo.sell.dao;

import com.demotwo.sell.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 1:47
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void save() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("222222");
        orderDetail.setOrderId("123456");
        orderDetail.setProductIcon("http://.....jpg");
        orderDetail.setProductPrice(new BigDecimal(600));
        orderDetail.setProductQuantity(1);
        orderDetail.setProductId("1234567");
        orderDetail.setProductName("测试商品名称2");
        OrderDetail orderDetail1 = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(orderDetail1);
    }

    @Test
    public void findByOrderId() {
        String orderId = "123456";
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        Assert.assertNotEquals(0 , orderDetailList.size());
    }
}