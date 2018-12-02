package com.demotwo.sell.dao;

import com.demotwo.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 0:05
 * @Desc: 测试ProductCategory增删改查
 */
@RunWith(SpringRunner.class)
@FixMethodOrder
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    /**
     * 增加
     */
    @Test
    @Ignore
    public void AInsertTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("测试1");
        productCategory.setCategoryType(1);
        productCategoryRepository.save(productCategory);
    }

    /**
     * 查询
     */
    @Test
    @Ignore
    public void findOneTest() {
        ProductCategory productCategory = productCategoryRepository.findOne(2);
        Assert.assertNotNull(productCategory);
    }

    /**
     * 更新: jpa中的更新和插入都是save方法。只是，更新一定要指定ID，底层实现根据ID做的where子句的晒选条件
     * 这里，如果实体类中没有显示的指定Date类型的属性的话，自动更新时间会自动更新 (数据库已经有自动更新设置：on update current_timestamp)
     */
    @Test
    @Ignore
    public void BupdateTest1() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);            /** 重点标识 */
        productCategory.setCategoryName("测试4");
        productCategory.setCategoryType(3);
        productCategoryRepository.save(productCategory);
    }

    /**
     * 但是如果在实体类中显示的添加了时间date类型，更新数据是不会自动更新时间的,因为这里的逻辑取出一条数据，再对数据做更新，取出来的时间的数据放在对象的里面
     * 这是因为我们查出来之后对象的updatetime属性被填充进去保存了，而这里只是把查出来的对象更新了一个其它的属性而已，所以还是原来的更新时间
     * 这里可以通过在类上增加一个注解@DynamicUpdate动态更新时间(只要更改(和源数据不一致的)数据就会自动更新时间)
     * 1. @DynamicUpdate：也就是说只要更新一个数据，这条记录整个所有数据都会被更新
     * 2. @Transactional(在测试里面使用的话)：我们在测试的时候是希望我们的数据库是干净的，所以使用此注解数据在测试后自动回滚，
     * 不会在数据库的表中存留测试数据
     */
    @Test
    @Transactional
    public void BupdateTest2() {
        ProductCategory productCategory = productCategoryRepository.findOne(2);
        productCategory.setCategoryName("测试2");
        productCategoryRepository.save(productCategory);
    }

    /**
     * 删除
     */
    @Test
    @Ignore
    public void deleteTest() {
        // 这里先增加一条记录再将其删掉
        /*
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("测试啦");
        productCategory.setCategoryType(6);
        productCategoryRepository.save(productCategory);
        */

        productCategoryRepository.delete(3);
    }

    @Test
    public void findMultipleProducuCategory() {
        List<Integer> categoryType = Arrays.asList(2, 3, 4);
        List<ProductCategory> productCategoryList = productCategoryRepository.findByCategoryTypeIn(categoryType);
        Assert.assertNotEquals(0, productCategoryList.size());
    }
}