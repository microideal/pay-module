package com.microideal.paymodule.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.microideal.paymodule.constant.WeChatProperty;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class MiniAppPayUtil {


    private static final String TRADE_ID_FORMAT = "yyyyMMddHHmmssSSS";
    private static final Integer TRADE_ID_RANDOM_NUM = 4;
    private static final Integer RANDOM_BASE = 10;

    public static String generateNonceStr(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static String generateTimestamp(){
        Long timestamp = new Date().getTime() / 1000;
        return timestamp.toString();
    }

    public static String generateTradeId(){
        String timeStamp = new SimpleDateFormat(TRADE_ID_FORMAT).format(new Date());
        int unit = (int)Math.pow(RANDOM_BASE, TRADE_ID_RANDOM_NUM - 1);
        Integer randomNum = (int)(Math.random()*unit*9+unit);
        return timeStamp + randomNum;
    }

    public static String generateWeChatNonceStr(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static String generateSign(Object request){
        Map<String, String> sortedMap = convertToSortedMap(request);
        String sortRequest = convertToStr(sortedMap) + "key=" + WeChatProperty.KEY;
        return DigestUtils.md5Hex(sortRequest).toUpperCase();
    }

    public static String generateMiniAppSecondSign(Object request){
        Map<String, String> sortedMap = convertWeChatMiniAppPayResponseToSortedMap(request);
        String sortRequest = convertToStr(sortedMap) + "key=" + WeChatProperty.KEY;
        return DigestUtils.md5Hex(sortRequest).toUpperCase();
    }

    public static Map<String, String> convertWeChatMiniAppPayResponseToSortedMap(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> sortedMap = objectMapper.convertValue(object, TreeMap.class);
        Map formatedMap = new TreeMap();
        sortedMap.forEach((key, value) -> {
            if(!key.equals("sign") && !key.equals("tradeId") && !key.equals("openid") && !key.equals("result")){
                JacksonXmlProperty jacksonXmlProperty = obtainJacksonXmlPropertyAnnotationByKey(object, key);
                formatedMap.put(jacksonXmlProperty.localName(), value);
            }
        });
        return formatedMap;
    }

    public static Map<String, String> convertToSortedMap(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> sortedMap = objectMapper.convertValue(object, TreeMap.class);
        Map formatedMap = new TreeMap();
        sortedMap.forEach((key, value) -> {
            if(!key.equals("sign")){
                JacksonXmlProperty jacksonXmlProperty = obtainJacksonXmlPropertyAnnotationByKey(object, key);
                formatedMap.put(jacksonXmlProperty.localName(), value);
            }
        });
        return formatedMap;
    }

    public static JacksonXmlProperty obtainJacksonXmlPropertyAnnotationByKey(Object object, String key){
        JacksonXmlProperty jacksonXmlProperty = null;
        try {
            jacksonXmlProperty = object.getClass().getDeclaredField(key).getAnnotation(JacksonXmlProperty.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return jacksonXmlProperty;
    }

    public static String convertToStr(Map<String, String> sortedMap){
        final String[] temp = {""};
        sortedMap.forEach((key, value) -> {
            if(!StringUtils.isEmpty(value)){
                temp[0] += key + "=" + value + "&";
            }
        });
        return temp[0];
    }
}
