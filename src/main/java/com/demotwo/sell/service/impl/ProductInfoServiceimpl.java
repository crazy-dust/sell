package com.demotwo.sell.service.impl;

import com.demotwo.sell.converter.ProductForm2ProductInfo;
import com.demotwo.sell.dao.ProductInfoRepository;
import com.demotwo.sell.dto.CartDTO;
import com.demotwo.sell.entity.ProductInfo;
import com.demotwo.sell.enums.ProductInfoStatusEnum;
import com.demotwo.sell.enums.ResultEnum;
import com.demotwo.sell.exception.SellException;
import com.demotwo.sell.form.ProductForm;
import com.demotwo.sell.service.ProductInfoService;
import com.demotwo.sell.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 20:00
 * @Desc:
 */
@Service
@CacheConfig(cacheNames = "product")
public class ProductInfoServiceimpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    @Cacheable(key = "#productId")
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpBy() {
        return productInfoRepository.findByProductStatus(ProductInfoStatusEnum.UP.getState());
    }

    @Override
    public Page<ProductInfo> findByAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    @CachePut(key = "#productInfo.productId")
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo update(ProductForm productForm) {

        //先删掉商品之前的图片
        ProductInfo productInfo = productInfoRepository.findOne(productForm.getProductId());
        ImageUtil.deleteShopImg(productInfo.getProductIcon());

        ProductInfo productInfo1 = save(ProductForm2ProductInfo.converter(productForm));
        return productInfo1;
    }

    @Override
    @Transactional
    public void incrBy(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());

            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }

            Integer number = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(number);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void delBy(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());

            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }

            Integer number = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(number < 0) {
                throw new SellException(ResultEnum.INVENTORYSHORTAGE);
            }
            productInfo.setProductStock(number);
            productInfoRepository.save(productInfo);
        }
    }
}
