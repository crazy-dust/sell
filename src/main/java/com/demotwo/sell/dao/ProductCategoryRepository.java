package com.demotwo.sell.dao;

import com.demotwo.sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 0:01
 * @Desc:
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);

}