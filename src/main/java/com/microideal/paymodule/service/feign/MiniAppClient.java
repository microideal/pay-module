package com.microideal.paymodule.service.feign;

import com.microideal.paymodule.builder.wechat.miniapp.WeChatMiniAppResponseDto;
import com.microideal.paymodule.config.WeChatMiniAppConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author microideal on 2018/7/19
 */
@FeignClient(value = "miniAppClient", url = "https://api.weixin.qq.com", configuration = WeChatMiniAppConfig.class)
public interface MiniAppClient {
    @PostMapping(value = "/sns/jscode2session", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    WeChatMiniAppResponseDto getOpenidByCode(@RequestParam(value = "appid") String appid,
                                             @RequestParam(value = "secret") String secret,
                                             @RequestParam(value = "js_code") String js_code,
                                             @RequestParam(value = "grant_type") String grant_type);
}
