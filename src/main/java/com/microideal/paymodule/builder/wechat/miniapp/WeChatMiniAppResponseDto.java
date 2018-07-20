package com.microideal.paymodule.builder.wechat.miniapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author microideal on 2018/7/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatMiniAppResponseDto {
    private String openid;
    private String session_key;
    private String unionid;

    private String errcode;
    private String errmsg;
}
