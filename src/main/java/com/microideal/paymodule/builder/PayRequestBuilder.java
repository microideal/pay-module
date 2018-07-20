package com.microideal.paymodule.builder;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.microideal.paymodule.builder.wechat.WeChatPayRequest;
import com.microideal.paymodule.builder.wechat.miniapp.WeChatMiniAppPayRequest;
import com.microideal.paymodule.constant.AlipayProperty;
import com.microideal.paymodule.constant.WeChatProperty;
import com.microideal.paymodule.utils.MiniAppPayUtil;
import com.microideal.paymodule.utils.PayUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.microideal.paymodule.utils.PayUtil.generateTradeId;

/**
 * @author microideal on 2018/7/18
 */
public class PayRequestBuilder {

    private static final Integer THREE_MINUTES = 180000;

    public static AlipayTradeAppPayRequest generateAlipayOrderRequest(Integer fee, String notifyUrl, String title, String remark){
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(remark);
        model.setSubject(title);
        model.setOutTradeNo(generateTradeId());
        model.setTimeExpire(obtainAlipayTimeExpire());
        Float floatFee = Float.valueOf(fee.toString()) / 100;
        model.setTotalAmount(floatFee.toString());
        model.setProductCode(AlipayProperty.MODEL_PRODUCT_CODE);
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        return request;
    }

    public static AlipayTradePrecreateRequestBuilder generateAlipayQRCodeRequest(Integer fee, String operatorId, String storeId, String notifyUrl, String title, String remark){
        Float totalAmount = Float.valueOf(fee.toString()) / 100;
        return new AlipayTradePrecreateRequestBuilder()
                .setSubject(title)
                .setTotalAmount(totalAmount.toString())
                .setOutTradeNo(generateTradeId())
                .setBody(remark)
                .setOperatorId(operatorId)
                .setStoreId(storeId)
                .setTimeoutExpress(AlipayProperty.QRCODE_MODEL_TIME_OUT_EXPRESS)
                .setNotifyUrl(notifyUrl);
    }

    public static WeChatPayRequest generateWeChatAppPayRequest(String ip, Integer fee, String notifyUrl, String title, String remark){
        WeChatPayRequest request =  WeChatPayRequest.builder()
                .appId(WeChatProperty.APP_ID)
                .mchId(WeChatProperty.MERCHANT_ID)
                .nonceStr(PayUtil.generateWeChatNonceStr())
                .signType(WeChatProperty.SIGN_TYPE)
                .body(title)
                .outTradeNo(PayUtil.generateTradeId())
                .totalFee(fee.toString())
                .spbillCreateIp(ip)
                .timeStart(obtainStartTime())
                .timeExpire(obtainEndTime())
                .notifyUrl(notifyUrl)
                .tradeType(WeChatProperty.APP_TRADE)
                .attach(remark)
                .build();
        request.setSign(PayUtil.generateSign(request));
        return request;
    }

    public static WeChatPayRequest generateWeChatQRCodePayRequest(String ip, Integer fee, String notifyUrl, String title, String remark){
        WeChatPayRequest request =  WeChatPayRequest.builder()
                .appId(WeChatProperty.APP_ID)
                .mchId(WeChatProperty.MERCHANT_ID)
                .nonceStr(PayUtil.generateWeChatNonceStr())
                .signType(WeChatProperty.SIGN_TYPE)
                .body(title)
                .outTradeNo(PayUtil.generateTradeId())
                .totalFee(fee.toString())
                .spbillCreateIp(ip)
                .notifyUrl(notifyUrl)
                .tradeType(WeChatProperty.QRCODE_TRADE)
                .attach(remark)
                .build();
        request.setSign(PayUtil.generateSign(request));
        return request;
    }

    public static WeChatMiniAppPayRequest generateWeChatMiniAppPayRequest(String ip, Integer fee, String openid, String notifyUrl, String title, String remark) {
        WeChatMiniAppPayRequest request = WeChatMiniAppPayRequest.builder()
                .appId(WeChatProperty.MINI_APP_ID)
                .mchId(WeChatProperty.MERCHANT_ID)
                .nonceStr(PayUtil.generateWeChatNonceStr())
                .signType(WeChatProperty.SIGN_TYPE)
                .body(title)
                .outTradeNo(PayUtil.generateTradeId())
                .totalFee(fee.toString())
                .timeStart(obtainStartTime())
                .timeExpire(obtainEndTime())
                .spbillCreateIp(ip)
                .notifyUrl(notifyUrl)
                .tradeType(WeChatProperty.MINI_APP_TRADE)
                .attach(remark)
                .openid(openid)
                .build();
        request.setSign(MiniAppPayUtil.generateSign(request));
        return request;
    }

    private static String obtainAlipayTimeExpire(){
        Date now = new Date();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(now.getTime() + THREE_MINUTES);
    }

    private static String obtainStartTime(){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    private static String obtainEndTime(){
        Date now = new Date();
        return new SimpleDateFormat("yyyyMMddHHmmss").format(now.getTime() + THREE_MINUTES);
    }
}
