package com.demotwo.sell.dao;

import com.demotwo.sell.entity.SellerInfo;
import com.demotwo.sell.util.RandomKeys;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: liudongyang
 * @Date: 2018/9/28 16:14
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(RandomKeys.generateRandomKeys());
        sellerInfo.setOpenId("abc");
        sellerInfo.setPassword("admin");
        sellerInfo.setUsername("admin");

        Assert.assertNotNull(sellerInfoRepository.save(sellerInfo));
    }

    @Test
    public void findByOpenId() {
        SellerInfo sellerInfo = sellerInfoRepository.findByOpenId("abc");
        Assert.assertEquals("abc", sellerInfo.getOpenId());
    }
}