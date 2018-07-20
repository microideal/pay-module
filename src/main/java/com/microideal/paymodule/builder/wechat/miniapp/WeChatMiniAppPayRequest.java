package com.microideal.paymodule.builder.wechat.miniapp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author microideal on 2018/7/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "xml")
public class WeChatMiniAppPayRequest {
    @JacksonXmlProperty(localName = "appid")
    private String appId;
    @JacksonXmlProperty(localName = "mch_id")
    private String mchId;
    @JacksonXmlProperty(localName = "device_info")
    private String deviceInfo; // 非必填
    @JacksonXmlProperty(localName = "nonce_str")
    private String nonceStr;
    @JacksonXmlProperty(localName = "sign")
    private String sign;
    @JacksonXmlProperty(localName = "sign_type")
    private String signType; // 非必填
    @JacksonXmlProperty(localName = "body")
    private String body;
    @JacksonXmlProperty(localName = "details")
    private String details; // 非必填
    @JacksonXmlProperty(localName = "attach")
    private String attach; // 非必填
    @JacksonXmlProperty(localName = "out_trade_no")
    private String outTradeNo;
    @JacksonXmlProperty(localName = "fee_type")
    private String feeType; // 非必填
    @JacksonXmlProperty(localName = "total_fee")
    private String totalFee;
    @JacksonXmlProperty(localName = "spbill_create_ip")
    private String spbillCreateIp;
    @JacksonXmlProperty(localName = "time_start")
    private String timeStart; // 非必填
    @JacksonXmlProperty(localName = "time_expire")
    private String timeExpire; // 非必填
    @JacksonXmlProperty(localName = "goods_tag")
    private String goodsTag; // 非必填
    @JacksonXmlProperty(localName = "notify_url")
    private String notifyUrl;
    @JacksonXmlProperty(localName = "trade_type")
    private String tradeType;
    @JacksonXmlProperty(localName = "limit_pay")
    private String limitPay; // 非必填
    @JacksonXmlProperty(localName = "scene_info")
    private String sceneInfo;
    @JacksonXmlProperty(localName = "openid")
    private String openid; //非必填，当tradeType为JSAPI是必传
}
