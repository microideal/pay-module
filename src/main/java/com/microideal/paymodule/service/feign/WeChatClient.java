package com.microideal.paymodule.service.feign;

import com.microideal.paymodule.builder.wechat.WeChatPayResponse;
import com.microideal.paymodule.builder.wechat.WeChatPayRequest;
import com.microideal.paymodule.builder.wechat.miniapp.WeChatMiniAppPayRequest;
import com.microideal.paymodule.config.WeChatCodecConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author microideal on 2018/7/18
 */
@FeignClient(value = "weChatClient", url = "https://api.mch.weixin.qq.com", configuration = WeChatCodecConfig.class)
public interface WeChatClient {
    @PostMapping(value = "/pay/unifiedorder", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    WeChatPayResponse unifiedOrder(@RequestBody WeChatPayRequest request);

    @PostMapping(value = "/pay/unifiedorder", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    WeChatPayResponse miniApppUnifiedOrder(@RequestBody WeChatMiniAppPayRequest request);
}
