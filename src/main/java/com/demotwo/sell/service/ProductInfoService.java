package com.demotwo.sell.service;

import com.demotwo.sell.dto.CartDTO;
import com.demotwo.sell.entity.ProductInfo;
import com.demotwo.sell.form.ProductForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 19:52
 * @Desc:
 */
public interface ProductInfoService {

    /**
     * 查找一个商品
     * @param productId
     * @return 商品
     */
    ProductInfo findOne(String productId);

    /**
     * 根据上架查找商品
     * @return 商品列表
     */
    List<ProductInfo> findUpBy();

    /**
     * 分页查询
     * @param pageable
     * @return 商品列表
     */
    Page<ProductInfo> findByAll(Pageable pageable);

    /**
     * 新增或者更新一个商品的信息
     * @param productInfo
     * @return 已经存储的商品
     */
    ProductInfo save(ProductInfo productInfo);

    ProductInfo update(ProductForm productForm);

    //加库存
    void incrBy(List<CartDTO> cartDTOList);

    //减库存
    void delBy(List<CartDTO> cartDTOList);
}
