package com.demotwo.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 21:16
 * @Desc:
 */
@Data
public class ProductVO {


    @JsonProperty("name")
    private String productCategoryName;

    @JsonProperty("type")
    private Integer productType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}