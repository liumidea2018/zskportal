package com.haohe.zskportal;

import com.haohe.zskportal.utils.AddressUtil;
import com.haohe.zskportal.utils.IPUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * @author 微笑の掩饰
 * @date 2019/7/4 11:19
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IPUtilTest {

    private AddressUtil addressUtil;

    @Before
    public void setUp(){
        addressUtil=new AddressUtil();
    }
    @After
    public void tearDown(){
        addressUtil=null;
    }
    @Test
    public void getCityInfo(){
        String ip = "220.248.12.158";
        String ip2 = "127.0.0.1";
        System.out.println(addressUtil.getCityInfo(ip));
        System.out.println(addressUtil.getCityInfo(ip2));
    }
}
