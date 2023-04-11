package com.atguigu.srb.sms;

import com.yyl.srb.sms.util.SmsProperties;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.security.RunAs;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)

public class UtilsTests {
    @Test
    public void testProperties(){
        System.out.println(SmsProperties.KEY_ID);
        System.out.println(SmsProperties.KEY_SECRET);
        System.out.println(SmsProperties.REGION_Id);
    }

}
