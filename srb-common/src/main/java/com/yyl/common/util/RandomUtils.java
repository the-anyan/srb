package com.yyl.common.util;

/**
 * 生成4或者是6位验证码
 */
public class RandomUtils {


    public static String getFourBitRandom(){
//        int code = length == 6 ? (int)(Math.random() * 1000000) : (int)(Math.random() * 10000);
//         String.valueOf(Math.random() * 10000);
        return String.valueOf(Math.random(  ) * 10000);

    }
}
