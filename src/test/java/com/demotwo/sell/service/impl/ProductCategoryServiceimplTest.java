package com.demotwo.sell.service.impl;

import com.demotwo.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 2:03
 * @Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceimplTest {

    @Autowired
    private ProductCategoryServiceimpl productCategoryServiceimpl;

    @Test
    public void findByCategory() {
        ProductCategory productCategory = productCategoryServiceimpl.findByCategory(2);
        Assert.assertEquals(new Integer(3), productCategory.getCategoryType());
    }

    @Test
    public void findByAll() {
        List<ProductCategory> productCategoryList = productCategoryServiceimpl.findByAll();
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = productCategoryServiceimpl.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("测试ok");
        productCategory.setCategoryType(8);
        ProductCategory productCategory1 = productCategoryServiceimpl.save(productCategory);
        System.out.println(productCategory1);
    }
}