package com.demotwo.sell.web.buyer;

import com.demotwo.sell.dto.OrderMasterDTO;
import com.demotwo.sell.enums.OrderStatusEnum;
import com.demotwo.sell.exception.SellException;
import com.demotwo.sell.service.OrderMasterService;
import com.demotwo.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author: liudongyang
 * @Date: 2018/9/24 15:50
 * @Desc:
 */
@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private PayService payService;

    /**
     * 微信客户端请求生成预支付订单
     * @param orderid 订单id
     * @param returnUrl 支付成功重定向的URL
     * @param map model模型数据
     * @return 需要生成JSAPI给微信客户端
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderid,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {

        //1. 查询订单是否存在
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderid);
        if(orderMasterDTO  == null) {
            log.info("【订单异常】 订单不存在, orderMasterDTO = {}", orderMasterDTO);
            throw new SellException(OrderStatusEnum.ORDER_NOT_EXIST);
        }

        //2. 支付订单
        PayResponse payResponse = payService.create(orderMasterDTO);
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);

        ModelAndView modelAndView = new ModelAndView("pay/create", map);
        return modelAndView;
    }

    /**
     * 微信异步通知
     * @param notifyData 微信发送的异步数据
     * @return 返回给微信处理结果
     */
    @PostMapping("/notify")
    public ModelAndView notifiy(String notifyData) {
        payService.notify(notifyData);

        return new ModelAndView("pay/success");
    }

}

