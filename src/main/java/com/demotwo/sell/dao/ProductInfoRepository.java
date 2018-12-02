package com.demotwo.sell.dao;

import com.demotwo.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 17:57
 * @Desc:
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);

}
