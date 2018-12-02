package com.demotwo.sell.web.seller;

import com.demotwo.sell.converter.ProductForm2ProductInfo;
import com.demotwo.sell.entity.ProductCategory;
import com.demotwo.sell.entity.ProductInfo;
import com.demotwo.sell.enums.ProductInfoStatusEnum;
import com.demotwo.sell.exception.SellException;
import com.demotwo.sell.form.ProductForm;
import com.demotwo.sell.service.ProductCategoryService;
import com.demotwo.sell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author: liudongyang
 * @Date: 2018/9/26 18:19
 * @Desc:
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 分页查询商品列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productInfoService.findByAll(pageRequest);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);

        ModelAndView modelAndView = new ModelAndView("product/list", map);
        return modelAndView;
    }

    /**
     * 商品的上下架
     * @param productid
     * @param status
     * @param map
     * @return
     */
    @GetMapping("/downAndup")
    public ModelAndView downProduct(@RequestParam("productid") String productid,
                                    @RequestParam("status") Integer status,
                                    Map<String, Object> map) {

        try {
            ProductInfo productInfo = productInfoService.findOne(productid);

            if(status == ProductInfoStatusEnum.UP.getState()) {
                productInfo.setProductStatus(ProductInfoStatusEnum.DOWN.getState());
            } else if(status == ProductInfoStatusEnum.DOWN.getState()) {
                productInfo.setProductStatus(ProductInfoStatusEnum.UP.getState());
            }

            productInfoService.save(productInfo);
        } catch (Exception e) {
            log.error("【商品下架】 找不到商品, productId = {}", productid);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ProductInfoStatusEnum.SUCCESS);
        map.put("url", "/sell/seller/product/list");

        ModelAndView modelAndView = new ModelAndView("common/success", map);
        return modelAndView;
    }

    /**
     * 修改商品的视图
     * @param productid
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productid", required = false) String productid,
                                   Map<String, Object> map) {

        ProductInfo productInfo = null;
        List<ProductCategory> productCategoryList = null;
        try {
            //查询商品
            if(productid != null && !"".equals(productid)) {
                productInfo = productInfoService.findOne(productid);
                map.put("productInfo", productInfo);
            }

            //查询所有类别
            productCategoryList =  productCategoryService.findByAll();
            map.put("productCategoryList", productCategoryList);
        } catch (Exception e) {
            log.error("【修改商品】 找不到商品, productId = {}", productid);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        return new ModelAndView("product/index", map);
    }


    /**
     * 商品的修改和新增！
     * @param productForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                               BindingResult bindingResult,
                               Map<String, Object> map) {
        //表单对象验证！
        if(bindingResult.hasErrors()) {
            log.info("【修改商品】 表单对象验证失败 error = {}", productForm.toString());
            map.put("msg", "修改商品出错");
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        ProductInfo productInfo = null;
        try {
            // 商品id为空是新增
            if(StringUtils.isEmpty(productForm.getProductId())) {
                //转换对象
                productInfo = ProductForm2ProductInfo.converter(productForm);
                productInfoService.save(productInfo);
            } else {
                productInfo = productInfoService.update(productForm);
            }
        } catch (SellException e) {
            log.info("【修改商品】 转换对象失败 error = {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ProductInfoStatusEnum.SUCCESS);
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
}

