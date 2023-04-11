package com.yyl.srb.core;

import com.yyl.srb.core.mapper.DictMapper;
import com.yyl.srb.core.pojo.entity.Dict;
import com.yyl.srb.core.pojo.entity.IntegralGrade;
import com.yyl.srb.core.service.IntegralGradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration
//@WebAppConfiguration
//@TestPropertySource(value = "classpath*:application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTemplateTests {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private IntegralGradeService integralGradeService;
    @Resource
    private DictMapper dictMapper;
    @Test
    public void saveDict(){
        Dict dict = dictMapper.selectById(1);
        System.out.println(dict);
        //向数据库中存储string类型的键值对，过期时间是五分钟
        redisTemplate.opsForValue().set("dict",dict,5, TimeUnit.MINUTES);
    }
    @Test
    public void geiDict(){
        Dict dict = (Dict)redisTemplate.opsForValue().get("dict");
        System.out.println(dict);
    }
    @Test
    public void selectList(){
        List<IntegralGrade> list = integralGradeService.list();
        System.out.println(list);

    }
}
