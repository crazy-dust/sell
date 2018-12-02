package com.demotwo.sell.service;

import com.demotwo.sell.entity.ProductCategory;

import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 1:53
 * @Desc:
 */
public interface ProductCategoryService {

    ProductCategory findByCategory(Integer categoryId);

    List<ProductCategory> findByAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);

    ProductCategory save(ProductCategory productCategory);
}
