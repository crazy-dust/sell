package com.demotwo.sell.web.seller;

import com.demotwo.sell.converter.CategoryForm2Category;
import com.demotwo.sell.entity.ProductCategory;
import com.demotwo.sell.form.CategoryForm;
import com.demotwo.sell.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @Date: 2018/9/28 14:17
 * @Desc:
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> productCategoryList = productCategoryService.findByAll();
        map.put("productCategoryList", productCategoryList);

        ModelAndView modelAndView = new ModelAndView("category/list", map);
        return modelAndView;
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryid", required = false) Integer cateoryId,
                              Map<String, Object> map) {
       if(cateoryId != null && !"".equals(cateoryId)) {
           ProductCategory productCategory = productCategoryService.findByCategory(cateoryId);
           map.put("productCategory", productCategory);
       }

        ModelAndView modelAndView = new ModelAndView("category/index", map);
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        //表单对象验证！
        if(bindingResult.hasErrors()) {
            log.info("【修改商品类别】 表单对象验证失败 error = {}", categoryForm.toString());
            map.put("msg", "修改商品类别出错");
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", map);
        }

        ProductCategory productCategory;
        try {
            productCategory = CategoryForm2Category.conver(categoryForm);
        } catch (Exception e) {
            log.error("【修改类别】 转换或存储出错", e.getMessage());
            map.put("msg", "对象转换失败");
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", map);
        }

        try {
            productCategoryService.save(productCategory);
        } catch (Exception e) {
            log.error("【修改类别】 转换或存储出错", e.getMessage());
            map.put("msg", "类别存储出错" + e.getMessage());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", "成功");
        map.put("url", "/sell/seller/category/list");
        ModelAndView modelAndView = new ModelAndView("common/success", map);
        return modelAndView;
    }
}