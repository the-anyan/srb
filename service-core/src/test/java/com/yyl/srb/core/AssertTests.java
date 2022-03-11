package com.yyl.srb.core;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 * 以优雅的 Assert(断言) 方式来校验业务的异常情况，消除 if else
 */
public class AssertTests {
    //if else 用法
    public void test1(){
        Object o = null;
        if (o == null){
            throw new IllegalArgumentException("用户不存在.");
        }
    }
    //断言的用法，
    @Test
    public void test2(){
        Object o =null;
        Assert.notNull(o,"用户不存在.");
    }
}
