package com.demotwo.sell.converter;

import com.demotwo.sell.dto.ImageHolder;
import com.demotwo.sell.entity.ProductCategory;
import com.demotwo.sell.entity.ProductInfo;
import com.demotwo.sell.enums.ProductInfoStatusEnum;
import com.demotwo.sell.exception.SellException;
import com.demotwo.sell.form.ProductForm;
import com.demotwo.sell.util.ImageUtil;
import com.demotwo.sell.util.PathUtil;
import com.demotwo.sell.util.RandomKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Author: liudongyang
 * @Date: 2018/9/26 23:24
 * @Desc:
 */
@Slf4j
public class ProductForm2ProductInfo {

    /**
     * 表单对象转换为实体类对象，提供储存功能！
     * @param productForm
     * @return
     */
    public static ProductInfo converter(ProductForm productForm) throws SellException {
        ProductInfo productInfo = new ProductInfo();

        try {
            //处理缩略图
            MultipartFile multipartFile = productForm.getProductIcon();

            if(multipartFile.isEmpty()) {
                throw new SellException(ProductInfoStatusEnum.FORM_IMAGES_NULL);
            }

            ImageHolder imageHolder = new ImageHolder(multipartFile.getInputStream(), multipartFile.getOriginalFilename());

            //判断有没有商品id属性，如果有的话则是修改逻辑，否则就是新增逻辑
            String productId = productForm.getProductId();
            if(productId == null || productId.equals("")) {
                productId = RandomKeys.generateRandomKeys();
            }

            String absolutePath = PathUtil.getShopImagePath(productId);
            String productIcon = ImageUtil.generateThumbnail(imageHolder, absolutePath);

            //处理其它的转换！
            productInfo.setProductId(productId);
            productInfo.setProductStatus(ProductInfoStatusEnum.UP.getState());
            productInfo.setProductName(productForm.getProductName());
            productInfo.setProductStock(Integer.parseInt(productForm.getProductStock()));
            productInfo.setProductDescription(productForm.getProductDescription());
            productInfo.setProductPrice(new BigDecimal(productForm.getProductPrice()));
            productInfo.setProductIcon(productIcon);
            productInfo.setCategoryType(productForm.getCategoryType());
        } catch (IOException e) {
            log.error("【商品表单对象转换失败】 error = {}", e.getMessage());
            throw new SellException(ProductInfoStatusEnum.PRODUCTFORM_CONVERTER_ERROR);
        }
        return productInfo;
    }
}