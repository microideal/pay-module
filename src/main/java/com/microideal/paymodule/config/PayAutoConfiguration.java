package com.microideal.paymodule.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.microideal.paymodule.constant.AlipayProperty;
import com.microideal.paymodule.service.AlipayService;
import com.microideal.paymodule.service.WeChatPayService;
import com.microideal.paymodule.service.feign.MiniAppClient;
import com.microideal.paymodule.service.feign.WeChatClient;
import com.microideal.paymodule.service.impl.AlipayServiceImpl;
import com.microideal.paymodule.service.impl.WeChatPayserviceImpl;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author microideal on 2018/7/18
 */
@Configuration
@EnableFeignClients(basePackages = {"com.microideal.paymodule.service.feign"})
public class PayAutoConfiguration {
    @Bean
    public AlipayClient alipayClient(){
        return new DefaultAlipayClient(AlipayProperty.SERVER_URL,
                AlipayProperty.APP_ID,
                AlipayProperty.APP_PRIVATE_KEY,
                AlipayProperty.FORMAT,
                AlipayProperty.CHARSET,
                AlipayProperty.ALIPAY_PUBLIC_KEY,
                AlipayProperty.SIGN_TYPE);
    }

    @Bean
    public AlipayTradeService alipayTradeService(){
        return new AlipayTradeServiceImpl.ClientBuilder()
                .setAppid(AlipayProperty.APP_ID)
                .setAlipayPublicKey(AlipayProperty.ALIPAY_PUBLIC_KEY)
                .setCharset(AlipayProperty.CHARSET)
                .setFormat(AlipayProperty.FORMAT)
                .setPrivateKey(AlipayProperty.APP_PRIVATE_KEY)
                .setSignType(AlipayProperty.SIGN_TYPE)
                .setGatewayUrl(AlipayProperty.SERVER_URL).build();
    }

    @Bean
    public AlipayService alipayService(AlipayClient alipayClient,AlipayTradeService alipayTradeService){
        return new AlipayServiceImpl(alipayClient,alipayTradeService);
    }

    @Bean
    public WeChatPayService weChatPayService(WeChatClient weChatClient,MiniAppClient miniAppClient){
        return new WeChatPayserviceImpl(weChatClient,miniAppClient);
    }
}
