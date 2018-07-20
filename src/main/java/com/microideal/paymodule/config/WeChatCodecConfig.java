package com.microideal.paymodule.config;

import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author microideal on 2018/7/18
 */
public class WeChatCodecConfig {
    @Bean
    public Decoder feignDecoder() {
        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
        List<MediaType> types = xmlConverter.getSupportedMediaTypes();
        List<MediaType> newTypes = new ArrayList<MediaType>();
        newTypes.add(MediaType.TEXT_PLAIN);
        for(MediaType t: types) {
            newTypes.add(t);
        }
        xmlConverter.setSupportedMediaTypes(newTypes);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(xmlConverter);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }
}
