package com.haohe.zskportal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 微笑の掩饰
 * @date 2019/6/26 19:22
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testInster(){//添加数据
        String token = UUID.randomUUID().toString();
        String name = "测试数据2";
        //有效时间
        Integer effectiveTime = 60;
        //存放内容
        String content = "12312312312";
        redisTemplate.opsForValue().set(String.format(name, token), content, effectiveTime, TimeUnit.SECONDS);
    }

    @Test
    public void testDelete(){//删除数据
        String token = UUID.randomUUID().toString();
        String name = "测试数据2";
        redisTemplate.opsForValue().getOperations().delete((String.format(name, token)));
    }

}
