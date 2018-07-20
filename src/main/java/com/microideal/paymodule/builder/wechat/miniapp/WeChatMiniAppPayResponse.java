package com.microideal.paymodule.builder.wechat.miniapp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.microideal.paymodule.builder.wechat.WeChatPayResponse;
import com.microideal.paymodule.constant.WeChatProperty;
import com.microideal.paymodule.utils.MiniAppPayUtil;
import com.microideal.paymodule.utils.PayUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author microideal on 2018/7/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeChatMiniAppPayResponse {
    @JacksonXmlProperty(localName = "appId")
    private String appId;
    @JacksonXmlProperty(localName = "package")
    private String pack;
    @JacksonXmlProperty(localName = "nonceStr")
    private String nonceStr;
    @JacksonXmlProperty(localName = "timeStamp")
    private String timestamp;
    @JacksonXmlProperty(localName = "signType")
    private String signType;
    private String sign;
    private String tradeId;
    private String openid;
    private String result;

    public WeChatMiniAppPayResponse(String result){
        this.setResult(result);
    }

    public static WeChatMiniAppPayResponse buildAppPayResponseDto(String openid, WeChatPayResponse response){
        WeChatMiniAppPayResponse weChatMiniAppPayResponse = WeChatMiniAppPayResponse.builder()
                .appId(response.getAppId())
                .pack("prepay_id=" + response.getPrepayId())
                .nonceStr(PayUtil.generateNonceStr())
                .timestamp(PayUtil.generateTimestamp())
                .signType(WeChatProperty.SIGN_TYPE)
                .openid(openid)
                .result(response.isSuccess() ? "SUCCESS" : "FAILURE")
                .build();
        weChatMiniAppPayResponse.setSign(MiniAppPayUtil.generateMiniAppSecondSign(weChatMiniAppPayResponse));
        weChatMiniAppPayResponse.setTradeId(response.getTradeId());
        return weChatMiniAppPayResponse;
    }
}
