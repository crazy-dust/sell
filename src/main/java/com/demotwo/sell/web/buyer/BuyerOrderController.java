package com.demotwo.sell.web.buyer;

import com.demotwo.sell.converter.OrderForm2OrderMasterDTO;
import com.demotwo.sell.dto.OrderMasterDTO;
import com.demotwo.sell.enums.OrderStatusEnum;
import com.demotwo.sell.exception.SellException;
import com.demotwo.sell.form.OrderForm;
import com.demotwo.sell.service.BuyerService;
import com.demotwo.sell.service.OrderMasterService;
import com.demotwo.sell.util.ResultVOUtil;
import com.demotwo.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liudongyang
 * @Date: 2018/9/17 19:13
 * @Desc:
 */
@RestController
@RequestMapping(value = "/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping(value = "/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error("【创建订单】 错误 参数不正确 orderForm = {}", orderForm);
            throw new SellException(OrderStatusEnum.ORDER_STATE_ERROR.getState(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderMasterDTO orderMasterDTO = OrderForm2OrderMasterDTO.Conver(orderForm);
        if(CollectionUtils.isEmpty(orderMasterDTO.getOrderDetailList())) {
            log.error("【创建订单】错误 购物车不能为空");
            throw new SellException(OrderStatusEnum.CART_CANNOTBEEMPTY);
        }

        //存储订单
        OrderMasterDTO orderMasterDTO1 = orderMasterService.create(orderMasterDTO);

        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", orderMasterDTO1.getOrderId());

        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping(value = "/list")
    public ResultVO<OrderMasterDTO> list(@RequestParam("openId") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {

        if(openid == null || page == null || size == null) {
            log.error("【订单列表】 错误, 参数出错, openId = {}, page = {}, size = {}", openid, page, size);
            throw new SellException(OrderStatusEnum.ORDER_PARAMETER_ERROR);
        }

        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderMasterDTO> orderMasterDTOPage = orderMasterService.findList(openid, pageRequest);

        return ResultVOUtil.success(orderMasterDTOPage.getContent());
    }

    //查询订单详情
    @GetMapping(value = "/detail")
    public ResultVO<OrderMasterDTO> detail(@RequestParam("openid") String openid,
                                           @RequestParam("orderId") String orderId) {

        OrderMasterDTO orderMasterDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderMasterDTO);
    }

    //取消订单
    @GetMapping(value = "/cancel")
    public ResultVO<OrderMasterDTO> cancel(@RequestParam("openid") String openid,
                                           @RequestParam("orderId") String orderId) {

        OrderMasterDTO orderMasterDTO = buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}