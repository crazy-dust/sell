package com.demotwo.sell.dao;

import com.demotwo.sell.entity.ProductInfo;
import com.demotwo.sell.util.RandomKeys;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 17:59
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    @Repeat(value = 20)
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(RandomKeys.generateRandomKeys());
        productInfo.setProductName("测试商品名称1");
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("测试描述1");
        productInfo.setProductIcon("测试小图链接1");
        productInfo.setProductPrice(new BigDecimal(123.34));
        productInfo.setProductStatus(1);
        productInfo.setProductStock(100);
        ProductInfo productInfo1 = productInfoRepository.save(productInfo);
        Assert.assertNotNull(productInfo1);

        ProductInfo productInfo2 = new ProductInfo();
        productInfo2.setProductId(RandomKeys.generateRandomKeys());
        productInfo2.setProductName("测试商品名称2");
        productInfo2.setCategoryType(3);
        productInfo2.setProductDescription("测试描述2");
        productInfo2.setProductIcon("测试小图链接2");
        productInfo2.setProductPrice(new BigDecimal(123.34));
        productInfo2.setProductStatus(1);
        productInfo2.setProductStock(100);
        ProductInfo productInfo2t = productInfoRepository.save(productInfo2);
        Assert.assertNotNull(productInfo2t);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList =  productInfoRepository.findByProductStatus(1);
        Assert.assertNotEquals(0, productInfoList.size());
    }
}