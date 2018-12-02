package com.demotwo.sell.service.impl;

import com.demotwo.sell.entity.ProductInfo;
import com.demotwo.sell.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 20:10
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceimplTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    @Test
    public void findUpBy() {
        List<ProductInfo> productInfoList = productInfoService.findUpBy();
    }

    @Test
    public void findByAll() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> productInfoPage = productInfoService.findByAll(pageRequest);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("12345678");
        productInfo.setProductName("测试商品名称3");
        productInfo.setCategoryType(8);
        productInfo.setProductDescription("测试描述3");
        productInfo.setProductIcon("测试小图链接3");
        productInfo.setProductPrice(new BigDecimal(123.34));
        productInfo.setProductStatus(1);
        productInfo.setProductStock(100);
        ProductInfo productInfo1 = productInfoService.save(productInfo);
        Assert.assertEquals(productInfo.getProductId(), productInfo1.getProductId());
    }
}