package com.haohe.zskportal;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author 微笑の掩饰
 * @date 2019/7/2 11:43
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActiveMQ {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Test
    public void contextLoads() throws InterruptedException {
        Destination destination = new ActiveMQQueue("sanji_sms");
        //验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        Map<String, String> map = new HashMap<>();
        map.put("mobile", "17610529205");
        map.put("template_code", "SMS_162736532");
        map.put("sign_name", "将何数据");
        map.put("param", "{\"code\":\"098761\"}");
        jmsMessagingTemplate.convertAndSend(destination,map);

    }
}
