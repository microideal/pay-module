package com.microideal.paymodule.builder.wechat;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

/**
 * @author microideal on 2018/7/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "xml")
public class WeChatPayResponse {
    @JacksonXmlProperty(localName = "return_code")
    private String returnCode;
    @JacksonXmlProperty(localName = "return_msg")
    private String returnMsg;
    @JacksonXmlProperty(localName = "appid")
    private String appId;
    @JacksonXmlProperty(localName = "mch_id")
    private String mchId;
    @JacksonXmlProperty(localName = "device_info")
    private String deviceInfo;
    @JacksonXmlProperty(localName = "nonce_str")
    private String nonceStr;
    private String sign;
    @JacksonXmlProperty(localName = "result_code")
    private String resultCode;
    @JacksonXmlProperty(localName = "err_code")
    private String errCode;
    @JacksonXmlProperty(localName = "err_code_des")
    private String errCodeDes;
    @JacksonXmlProperty(localName = "trade_type")
    private String tradeType;
    @JacksonXmlProperty(localName = "prepay_id")
    private String prepayId;
    @JacksonXmlProperty(localName = "code_url")
    private String codeUrl;
    private String tradeId; // 非微信服务器返回参数

    public void populateOutTradeId(String tradeId){
        this.setTradeId(tradeId);
    }

    public Boolean isSuccess(){
        return "SUCCESS".equals(this.returnCode) && "SUCCESS".equals(this.resultCode);
    }
}
