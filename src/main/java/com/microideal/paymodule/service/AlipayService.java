package com.microideal.paymodule.service;

import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;

/**
 * @author microideal on 2018/7/17
 */
public interface AlipayService {
    AlipayTradeAppPayResponse generateAppPayOrder(Integer fee, String notifyUrl, String title, String remark);
    AlipayF2FPrecreateResult generateQRCodePayOrder(Integer fee, String operatorId, String storeId, String notifyUrl, String title, String remark);
}
