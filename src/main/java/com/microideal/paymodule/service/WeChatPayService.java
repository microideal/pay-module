package com.microideal.paymodule.service;

import com.microideal.paymodule.builder.wechat.WeChatPayResponse;
import com.microideal.paymodule.builder.wechat.app.WeChatAppPayResponse;
import com.microideal.paymodule.builder.wechat.miniapp.WeChatMiniAppPayResponse;

/**
 * @author microideal on 2018/7/18
 */
public interface WeChatPayService {
    WeChatAppPayResponse generateAppPayOrder(String ip, Integer fee, String notifyUrl, String title, String remark);
    WeChatPayResponse generateQRCodePayOrder(String ip, Integer fee, String notifyUrl, String title, String remark);
    WeChatMiniAppPayResponse generateMiniAppPayOrder(String ip, Integer fee, String code, String notifyUrl, String title, String remark);
}
