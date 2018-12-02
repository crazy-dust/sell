package com.demotwo.sell.web.buyer;
import com.demotwo.sell.entity.ProductCategory;
import com.demotwo.sell.entity.ProductInfo;
import com.demotwo.sell.service.ProductCategoryService;
import com.demotwo.sell.service.ProductInfoService;
import com.demotwo.sell.util.ResultVOUtil;
import com.demotwo.sell.vo.ProductInfoVO;
import com.demotwo.sell.vo.ProductVO;
import com.demotwo.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 20:49
 * @Desc:
 */
@RestController
@RequestMapping("/buyer/product")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/list")
    public ResultVO getList() {
        //1. 查询所有的上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpBy();

        //2. 查询类目(一次性查询)根据上架的商品的类目来查找上架的类目
        List<Integer> productCategoryType = new ArrayList<>();
        for (ProductInfo productInfo : productInfoList) {
            productCategoryType.add(productInfo.getCategoryType());
        }

        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(productCategoryType);
        List<ProductVO> productVOList = new ArrayList<ProductVO>();

        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setProductCategoryName(productCategory.getCategoryName());
            productVO.setProductType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();
            for (ProductInfo productInfo : productInfoList) {
                if(productCategory.getCategoryType().equals(productInfo.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);   //一个类别对应多个商品详情
            productVOList.add(productVO);   //多个类别
        }

        //3. 数据拼装
        return ResultVOUtil.success(productVOList);
    }

}
