package com.yyl.srb.sms.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 常量 读取工具类
 */
@Setter//调用Setter为成员变量负值
@Getter
@Component
@ConfigurationProperties(prefix = "aliyun.sms")//调用prefix要写到最后一个"."符号之前
public class SmsProperties implements InitializingBean {
    private String regionId;
    private String keyId;
    private String keySecret;
    private String templateCode;
    private String signName;

    public static String REGION_Id;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String TEMPLATE_CODE;
    public static String SIGN_NAME;

    //当私有成员被赋值后，此方法自动被调用，从而初始化常量
    @Override
    public void afterPropertiesSet() throws Exception {

        REGION_Id = regionId;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        TEMPLATE_CODE = templateCode;
        SIGN_NAME = signName;

    }
}
