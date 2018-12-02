package com.demotwo.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @Author: liudongyang
 * @Date: 2018/9/28 14:55
 * @Desc:
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    @NotEmpty(message = "类别名称不为空")
    private String categoryName;

    @NotNull(message = "类别类型不能为空")
    private Integer categoryType;
}