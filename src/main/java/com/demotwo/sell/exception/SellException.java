package com.demotwo.sell.exception;

import com.demotwo.sell.enums.OrderStatusEnum;
import com.demotwo.sell.enums.PayStatusEnum;
import com.demotwo.sell.enums.ProductInfoStatusEnum;
import com.demotwo.sell.enums.ResultEnum;
import lombok.Getter;

import java.io.Serializable;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 18:10
 * @Desc:
 */
@Getter
public class SellException extends RuntimeException {

    private static final long serialVersionUID = -5848934119515723657L;

    private Integer state;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getStateInfo());
        this.state  = resultEnum.getState();
    }

    public SellException(OrderStatusEnum orderStatusEnum) {
        super(orderStatusEnum.getStateInfo());
        this.state = orderStatusEnum.getState();
    }

    public SellException(PayStatusEnum payStatusEnum) {
        super(payStatusEnum.getStateInfo());
        this.state = payStatusEnum.getState();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.state = code;
    }

    public SellException(ProductInfoStatusEnum noExistsProduct) {
        super(noExistsProduct.getStateInfo());
        this. state = noExistsProduct.getState();
    }
}

