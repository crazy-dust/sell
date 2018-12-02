package com.demotwo.sell.service.impl;

import com.demotwo.sell.config.wechat.WechatAccountConfig;
import com.demotwo.sell.dto.OrderMasterDTO;
import com.demotwo.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: liudongyang
 * @Date: 2018/9/29 17:30
 * @Desc:
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    /**
     * 推送模板消息, 这里的模板消息没有抛出异常是因为这里的逻辑相对于完结订单来说模板消息没有那么重要，
     * 不能因为模板消息推送失败而影响整个完结订单的逻辑
     * @param orderMasterDTO
     */
    @Override
    public void orderStatus(OrderMasterDTO orderMasterDTO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(wechatAccountConfig.getTemplateId().get("templateId"));       // 模板id
        wxMpTemplateMessage.setToUser(orderMasterDTO.getBuyerOpenId());           //推送的openid

        List<WxMpTemplateData> wxMpTemplateDataList = Arrays.asList(
                new WxMpTemplateData("信息的名字1", "信息的值1"),
                new WxMpTemplateData("信息的名字2", "信息的值2"),
                new WxMpTemplateData("信息的名字3", "信息的值3")
        );
        wxMpTemplateMessage.setData(wxMpTemplateDataList);

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息推送】 失败 {}", e.getMessage());
        }
    }
}
