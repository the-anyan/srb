package com.yyl.srb.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.yyl.srb","com.yyl.common"})
public class ServiceSmsApplication {

    public static void main(String[] args){
        SpringApplication.run(ServiceSmsApplication.class,args);
    }
}
