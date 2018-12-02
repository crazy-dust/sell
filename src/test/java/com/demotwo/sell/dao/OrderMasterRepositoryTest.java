package com.demotwo.sell.dao;

import com.demotwo.sell.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 1:12
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    public static final String openId = "110110";

    @Test
    public void save() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234568");
        orderMaster.setBuyerOpenId("110110");
        orderMaster.setOrderAmount(new BigDecimal(22));
        orderMaster.setBuyerName("订单人的名字2");
        orderMaster.setBuyerAddress("订单人的地址2");
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);
        orderMaster.setBuyerPhone("2222222222");
        OrderMaster orderMaster1 = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(orderMaster1);
    }

    @Test
    public void findByBuyerOpenId() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenId(openId, pageRequest);
        System.out.println(orderMasterPage.getTotalElements());
    }
}