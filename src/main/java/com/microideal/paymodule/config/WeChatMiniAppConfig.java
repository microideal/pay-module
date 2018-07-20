package com.microideal.paymodule.config;

import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WeChatMiniAppConfig {
    @Bean
    public Decoder feignDecoder() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> types = jsonConverter.getSupportedMediaTypes();
        List<MediaType> newTypes = new ArrayList<MediaType>();
        newTypes.add(MediaType.TEXT_PLAIN);
        for(MediaType t: types) {
            newTypes.add(t);
        }
        jsonConverter.setSupportedMediaTypes(newTypes);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jsonConverter);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }
}
