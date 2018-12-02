package com.demotwo.sell.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
/**
 * @Author: liudongyang
 * @Date: 2018/9/26 23:03
 * @Desc:
 */
@Data
public class ProductForm {

    private String productId;

    @NotNull(message = "商品名必须填写")
    private String productName;

    @NotNull(message = "商品价格不能为空")
    private String productPrice;

    @NotNull(message = "库存不能为空")
    private String productStock;

    @NotNull(message = "商品描述不能为空")
    private String productDescription;

    @NotNull(message = "商品缩略图不能为空")
    private MultipartFile productIcon;

    @NotNull(message = "商品类目不能为空")
    private Integer categoryType;

}