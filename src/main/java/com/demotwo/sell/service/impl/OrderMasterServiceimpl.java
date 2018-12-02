package com.demotwo.sell.service.impl;

import com.demotwo.sell.converter.OrderMaster2OrderMasterDTO;
import com.demotwo.sell.dao.OrderDetailRepository;
import com.demotwo.sell.dao.OrderMasterRepository;
import com.demotwo.sell.dto.CartDTO;
import com.demotwo.sell.dto.OrderMasterDTO;
import com.demotwo.sell.entity.OrderDetail;
import com.demotwo.sell.entity.OrderMaster;
import com.demotwo.sell.entity.ProductInfo;
import com.demotwo.sell.enums.OrderStatusEnum;
import com.demotwo.sell.enums.PayStatusEnum;
import com.demotwo.sell.enums.ResultEnum;
import com.demotwo.sell.exception.SellException;
import com.demotwo.sell.service.*;
import com.demotwo.sell.util.RandomKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: liudongyang
 * @Date: 2018/9/16 17:50
 * @Desc:
 */
@Service
@Slf4j
public class OrderMasterServiceimpl implements OrderMasterService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;

    /**
     * 创建订单
     * @param orderMasterDTO
     * @return
     */
    @Override
    @Transactional
    public OrderMasterDTO create(OrderMasterDTO orderMasterDTO) {

        //  -----订单明细的处理-----
        String orderId = RandomKeys.generateRandomKeys();
        BigDecimal orderTotalPrice = new BigDecimal(0);

        List<CartDTO> cartDTOList = new ArrayList<>();
        /** 1. 查询商品(单价，数量) */
        for (OrderDetail orderDetail : orderMasterDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());

            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }

            /** 2. 计算总价 */
            // 首先 计算库存够不够
            Integer number = productInfo.getProductStock() - orderDetail.getProductQuantity();
            if(number < 0) {
                throw new SellException(ResultEnum.INVENTORYSHORTAGE);
            }
            orderTotalPrice = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderTotalPrice);

            /** 订单详情入库 */
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(RandomKeys.generateRandomKeys());
            orderDetailRepository.save(orderDetail);

            CartDTO cartDTO = new CartDTO();
            cartDTO.setProductId(productInfo.getProductId());
            cartDTO.setProductQuantity(orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }

        /** 3. 存入数据库(orderMaster、orderDetail) */
        OrderMaster orderMaster = new OrderMaster();
        orderMasterDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        orderMaster.setOrderAmount(orderTotalPrice);
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getState());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getState());
        orderMasterRepository.save(orderMaster);

        /** 4. 商品数据库中具体的商品扣库存 */
        productInfoService.delBy(cartDTOList);


        //websocket发送下单通知
        webSocket.setMessage(orderId);

        return orderMasterDTO;
    }

    /**
     *根据订单号查找订单和订单明细
     * @param orderId
     * @return
     */
    @Override
    public OrderMasterDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster == null) {
            throw new SellException(OrderStatusEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
        if(orderDetailList.isEmpty()) {
            throw new SellException(OrderStatusEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);
        orderMasterDTO.setOrderDetailList(orderDetailList);

        return orderMasterDTO;
    }

    /**
     * 分页展示订单(不包括订单明细)
     * @param buyerOpenId
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderMasterDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenId(buyerOpenId, pageable);

        Page<OrderMasterDTO> orderMasterDTOPage = new PageImpl<OrderMasterDTO>(OrderMaster2OrderMasterDTO.converter(orderMasterPage.getContent()),
                pageable,
                orderMasterPage.getTotalElements());
        return orderMasterDTOPage;
    }

    /**
     * 取消订单
     * @param orderMasterDTO
     * @return
     */
    @Override
    @Transactional
    public OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO) {

        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        //1. 判断订单状态, 这里逻辑是只可以取消新下单的订单
        if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getState())) {
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(OrderStatusEnum.ORDER_STATE_ERROR);
        }

        //修改订单状态
        orderMasterDTO.setOrderStatus(OrderStatusEnum.CANCEL.getState());
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(OrderStatusEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderMasterDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderMasterDTO);
            throw new SellException(OrderStatusEnum.ORDER_DETAIL_EMPTY);
        }

        List<CartDTO> cartDTOList = new ArrayList<>();
        for (OrderDetail orderDetail : orderMasterDTO.getOrderDetailList()) {
            CartDTO cartDTO = new CartDTO();
            cartDTO.setProductId(orderDetail.getProductId());
            cartDTO.setProductQuantity(orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }

        productInfoService.incrBy(cartDTOList);

        //如果已支付, 需要退款
        if (orderMasterDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getState())) {
            payService.refund(orderMasterDTO);
        }
        return orderMasterDTO;
    }

    /**
     * 完结订单
     * @param orderMasterDTO
     * @return
     */
    @Override
    @Transactional
    public OrderMasterDTO finsh(OrderMasterDTO orderMasterDTO)  {
        //1. 判断订单状态, 这里逻辑是只可以完结新下单的订单
        if(!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getState())) {
            log.error("【完结订单】 订单状态不正确, orderId = {}, orderStatus = {}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(OrderStatusEnum.ORDER_STATE_ERROR);
        }

        //2. 修改状态
        orderMasterDTO.setOrderStatus(OrderStatusEnum.FINSHED.getState());

        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        orderMasterRepository.save(orderMaster);

        //3. 推送模板消息, 这里的模板消息没有抛出异常是因为这里的逻辑相对于完结订单来说模板消息没有那么重要，
        // 不能因为模板消息推送失败而影响整个完结订单的逻辑
        pushMessageService.orderStatus(orderMasterDTO);

        return orderMasterDTO;
    }

    /**
     * 改变订单支付的状态
     * @param orderMasterDTO
     * @return
     */
    @Override
    @Transactional
    public OrderMasterDTO paid(OrderMasterDTO orderMasterDTO) {
        //1. 判断订单状态, 这里逻辑是只可以支付新下单的订单
        if(!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getState())) {
            log.error("【支付】 订单状态不正确, orderId = {}, orderStatus = {}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(OrderStatusEnum.ORDER_STATE_ERROR);
        }

        //2. 判断支付状态, 这里逻辑是只可以支付正在等待支付的订单
        if(!orderMasterDTO.getPayStatus().equals(PayStatusEnum.WAIT.getState())) {
            log.error("【支付订单】 订单状态不正确, orderId = {}, payStatus = {}", orderMasterDTO.getOrderId(), orderMasterDTO.getPayStatus());
            throw new SellException(PayStatusEnum.PAY_STATE_ERROR);
        }

        orderMasterDTO.setPayStatus(PayStatusEnum.SUCCESS.getState());

        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        orderMasterRepository.save(orderMaster);
        return orderMasterDTO;
    }

    @Override
    public Page<OrderMasterDTO> findByList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderMasterDTO> orderMasterList = OrderMaster2OrderMasterDTO.converter(orderMasterPage.getContent());

        Page<OrderMasterDTO> orderMasterDTOPage = new PageImpl<>(orderMasterList, pageable, orderMasterPage.getTotalElements());
        return orderMasterDTOPage;
    }
}

