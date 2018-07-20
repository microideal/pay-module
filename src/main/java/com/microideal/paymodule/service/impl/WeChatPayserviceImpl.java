package com.microideal.paymodule.service.impl;

import com.microideal.paymodule.builder.PayRequestBuilder;
import com.microideal.paymodule.builder.wechat.WeChatPayResponse;
import com.microideal.paymodule.builder.wechat.WeChatPayRequest;
import com.microideal.paymodule.builder.wechat.app.WeChatAppPayResponse;
import com.microideal.paymodule.builder.wechat.miniapp.WeChatMiniAppPayRequest;
import com.microideal.paymodule.builder.wechat.miniapp.WeChatMiniAppPayResponse;
import com.microideal.paymodule.builder.wechat.miniapp.WeChatMiniAppResponseDto;
import com.microideal.paymodule.constant.WeChatProperty;
import com.microideal.paymodule.service.WeChatPayService;
import com.microideal.paymodule.service.feign.MiniAppClient;
import com.microideal.paymodule.service.feign.WeChatClient;
import org.springframework.util.StringUtils;

/**
 * @author microideal on 2018/7/18
 */
public class WeChatPayserviceImpl implements WeChatPayService {

    private final WeChatClient weChatClient;
    private final MiniAppClient miniAppClient;

    public WeChatPayserviceImpl(WeChatClient weChatClient,MiniAppClient miniAppClient) {
        this.weChatClient = weChatClient;
        this.miniAppClient = miniAppClient;
    }

    @Override
    public WeChatAppPayResponse generateAppPayOrder(String ip, Integer fee, String notifyUrl, String title, String remark) {
        WeChatPayRequest request = PayRequestBuilder.generateWeChatAppPayRequest(ip, fee, notifyUrl, title, remark);
        WeChatPayResponse response = weChatClient.unifiedOrder(request);
        response.populateOutTradeId(request.getOutTradeNo());
        return WeChatAppPayResponse.buildAppPayResponse(response);
    }

    @Override
    public WeChatPayResponse generateQRCodePayOrder(String ip, Integer fee, String notifyUrl, String title, String remark) {
        WeChatPayRequest request = PayRequestBuilder.generateWeChatQRCodePayRequest(ip, fee, notifyUrl, title, remark);
        WeChatPayResponse response = weChatClient.unifiedOrder(request);
        response.populateOutTradeId(request.getOutTradeNo());
        return response;
    }

    @Override
    public WeChatMiniAppPayResponse generateMiniAppPayOrder(String ip, Integer fee, String code, String notifyUrl, String title, String remark) {
        WeChatMiniAppResponseDto weChatMiniAppResponseDto = miniAppClient.getOpenidByCode(WeChatProperty.MINI_APP_ID, WeChatProperty.MINI_APP_SECRET, code ,WeChatProperty.MINI_APP_GTANT_TYPE);
        if(StringUtils.isEmpty(weChatMiniAppResponseDto.getOpenid())){
            return new WeChatMiniAppPayResponse("FAILURE");
        }
        WeChatMiniAppPayRequest request = PayRequestBuilder.generateWeChatMiniAppPayRequest(ip, fee, weChatMiniAppResponseDto.getOpenid(), notifyUrl, title, remark);
        WeChatPayResponse response = weChatClient.miniApppUnifiedOrder(request);
        response.populateOutTradeId(request.getOutTradeNo());
        return WeChatMiniAppPayResponse.buildAppPayResponseDto(weChatMiniAppResponseDto.getOpenid(), response);
    }
}
