package com.demotwo.sell.converter;

import com.demotwo.sell.entity.ProductCategory;
import com.demotwo.sell.form.CategoryForm;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @Author: liudongyang
 * @Date: 2018/9/28 15:00
 * @Desc:
 */
public class CategoryForm2Category {

    public static ProductCategory conver(CategoryForm categoryForm) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(categoryForm, productCategory);
        return productCategory;
    }
}