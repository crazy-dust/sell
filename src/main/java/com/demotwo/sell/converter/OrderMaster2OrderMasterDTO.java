package com.demotwo.sell.converter;

import com.demotwo.sell.dto.OrderMasterDTO;
import com.demotwo.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/17 0:58
 * @Desc:
 */
public class OrderMaster2OrderMasterDTO {

    public static OrderMasterDTO converter(OrderMaster orderMaster) {
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);
        return orderMasterDTO;
    }

    public static List<OrderMasterDTO> converter(List<OrderMaster> orderMasterList) {
        List<OrderMasterDTO> orderMasterDTOList = new ArrayList<OrderMasterDTO>();

        for (OrderMaster orderMaster : orderMasterList) {
            OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
            BeanUtils.copyProperties(orderMaster, orderMasterDTO);
            orderMasterDTOList.add(orderMasterDTO);
        }

        return orderMasterDTOList;
    }

}



