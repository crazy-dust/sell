package com.demotwo.sell.converter;

import com.demotwo.sell.dto.OrderMasterDTO;
import com.demotwo.sell.entity.OrderDetail;
import com.demotwo.sell.entity.OrderMaster;
import com.demotwo.sell.enums.ResultEnum;
import com.demotwo.sell.exception.SellException;
import com.demotwo.sell.form.OrderForm;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/17 19:22
 * @Desc:
 */
@Slf4j
public class OrderForm2OrderMasterDTO {

    public static OrderMasterDTO Conver(OrderForm orderForm) {
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        orderMasterDTO.setBuyerName(orderForm.getName());
        orderMasterDTO.setBuyerAddress(orderForm.getAddress());
        orderMasterDTO.setBuyerOpenId(orderForm.getOpenId());
        orderMasterDTO.setBuyerPhone(orderForm.getPhone());

        String str = orderForm.getItems();

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, OrderDetail.class);
            orderDetailList = objectMapper.readValue(str, javaType);
            orderMasterDTO.setOrderDetailList(orderDetailList);
        } catch (IOException e) {
            log.error("【JSON 数据转换异常】" + str);
            throw new SellException(ResultEnum.PARAMETER_ERROR);
        }
        return orderMasterDTO;
    }

}