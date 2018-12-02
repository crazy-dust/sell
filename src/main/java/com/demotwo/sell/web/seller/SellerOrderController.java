package com.demotwo.sell.web.seller;

import com.demotwo.sell.dto.OrderMasterDTO;
import com.demotwo.sell.entity.OrderDetail;
import com.demotwo.sell.enums.OrderStatusEnum;
import com.demotwo.sell.exception.SellException;
import com.demotwo.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @Author: liudongyang
 * @Date: 2018/9/25 20:22
 * @Desc:
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 分页查询订单列表
     * @param page 第几页，从第一页开始
     * @param size 一页有多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderMasterDTO> orderMasterDTOPage = orderMasterService.findByList(request);
        map.put("orderMasterDTOPage", orderMasterDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("OrderStatusEnum", OrderStatusEnum.CANCEL);
//        orderMasterDTOPage.getTotalPages()

        ModelAndView modelAndView = new ModelAndView("order/list", map);
        return modelAndView;
    }

    /**
     * 取消订单
     * @param orderid
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderid") String orderid,
                               Map<String, Object> map) {
        OrderMasterDTO orderMasterDTO = null;
        try {
            orderMasterDTO = orderMasterService.findOne(orderid);

            orderMasterService.cancel(orderMasterDTO);
        } catch (SellException e) {
            log.error("【取消订单】 发生异常", e.getMessage());

            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", OrderStatusEnum.SUCCESS);
        map.put("url", "/sell/seller/order/list");
        ModelAndView modelAndView = new ModelAndView("common/success", map);
        return modelAndView;
    }

    /**
         * 查询订单详情
     * @param orderid
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderid") String orderid,
                               Map<String, Object> map) {
        OrderMasterDTO orderMasterDTO = null;
        List<OrderDetail> orderDetailList = null;
        try {
            orderMasterDTO = orderMasterService.findOne(orderid);

            orderDetailList = orderMasterDTO.getOrderDetailList();
        } catch (SellException e) {
            log.error("【查询订单详情】 发生异常", e.getMessage());

            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("orderDetailList", orderDetailList);
        map.put("orderMasterDTO", orderMasterDTO);
        map.put("orderid", orderid);

        ModelAndView modelAndView = new ModelAndView("order/detail", map);
        return modelAndView;
    }

    /**
     * 完结订单
     * @param orderid
     * @param map
     * @return
     */
    @GetMapping("/finsh")
    public ModelAndView finsh(@RequestParam("orderid") String orderid,
                              Map<String, Object> map) {
        OrderMasterDTO orderMasterDTO = null;
        List<OrderDetail> orderDetailList = null;
        try {
            orderMasterDTO = orderMasterService.findOne(orderid);

            orderMasterService.finsh(orderMasterDTO);
        } catch (SellException e) {
            log.error("【查询订单详情】 发生异常", e.getMessage());

            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", OrderStatusEnum.SUCCESS);
        map.put("url", "/sell/seller/order/list");
        ModelAndView modelAndView = new ModelAndView("common/finsh", map);
        return modelAndView;
    }
}