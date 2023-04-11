package com.yyl.common.util;

/**
 * 正则表达式，提供手机号码验证
 */
public class RegexValidateUtils{

    public static boolean checkCellphone(String phone){

        return phone.matches("1[3-9]\\d{9}");

    }
}
