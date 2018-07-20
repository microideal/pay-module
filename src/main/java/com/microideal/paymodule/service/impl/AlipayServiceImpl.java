package com.microideal.paymodule.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.microideal.paymodule.builder.PayRequestBuilder;
import com.microideal.paymodule.service.AlipayService;

/**
 * @author microideal on 2018/7/17
 */
public class AlipayServiceImpl implements AlipayService {

    private final AlipayClient alipayClient;
    private final AlipayTradeService alipayTradeService;

    public AlipayServiceImpl(AlipayClient alipayClient,AlipayTradeService alipayTradeService){
        this.alipayClient = alipayClient;
        this.alipayTradeService = alipayTradeService;
    }

    @Override
    public AlipayTradeAppPayResponse generateAppPayOrder(Integer fee, String notifyUrl, String title, String remark) {
        AlipayTradeAppPayRequest request = PayRequestBuilder.generateAlipayOrderRequest(fee, notifyUrl, title,remark);
        AlipayTradeAppPayResponse response = null;
        try {
            response = alipayClient.sdkExecute(request);
            response.setOutTradeNo(((AlipayTradeAppPayModel)request.getBizModel()).getOutTradeNo());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public AlipayF2FPrecreateResult generateQRCodePayOrder(Integer fee, String operatorId, String storeId, String notifyUrl, String title, String remark) {
        AlipayTradePrecreateRequestBuilder request = PayRequestBuilder.generateAlipayQRCodeRequest(fee, operatorId, storeId, notifyUrl, title, remark);
        return alipayTradeService.tradePrecreate(request);
    }
}
