package com.microideal.paymodule.builder.wechat.app;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.microideal.paymodule.builder.wechat.WeChatPayResponse;
import com.microideal.paymodule.constant.WeChatProperty;
import com.microideal.paymodule.utils.PayUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author microideal on 2018/7/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeChatAppPayResponse {
    @JacksonXmlProperty(localName = "appid")
    private String appId;
    @JacksonXmlProperty(localName = "partnerid")
    private String partnerId;
    @JacksonXmlProperty(localName = "prepayid")
    private String prepayId;
    @JacksonXmlProperty(localName = "package")
    private String pack;
    @JacksonXmlProperty(localName = "noncestr")
    private String nonceStr;
    @JacksonXmlProperty(localName = "timestamp")
    private String timestamp;
    @JacksonXmlProperty(localName = "sign")
    private String sign;

    private String tradeId;//交易id，不参与签名
    private String result;//成功标志，不参与签名


    public static WeChatAppPayResponse buildAppPayResponse(WeChatPayResponse response){
        if(!response.isSuccess()){
            return buildFailureResponse();
        }
        return buildSuccessResponse(response);
    }

    private static WeChatAppPayResponse buildSuccessResponse(WeChatPayResponse response){
        WeChatAppPayResponse weChatAppPayResponse = WeChatAppPayResponse.builder()
                .appId(response.getAppId())
                .partnerId(response.getMchId())
                .prepayId(response.getPrepayId())
                .pack(WeChatProperty.PACKAGE)
                .nonceStr(PayUtil.generateNonceStr())
                .timestamp(PayUtil.generateTimestamp())
                .tradeId(response.getTradeId())
                .result("SUCCESS")
                .build();
        weChatAppPayResponse.setSign(PayUtil.generateSign(weChatAppPayResponse));
        return weChatAppPayResponse;
    }

    private static WeChatAppPayResponse buildFailureResponse(){
        return WeChatAppPayResponse.builder()
                .result("FAILURE")
                .build();
    }
}
