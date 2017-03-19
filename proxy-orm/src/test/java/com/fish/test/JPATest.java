package com.fish.test;

import com.fish.proxy.bean.scanner.RequestResult;
import com.fish.proxy.bean.scanner.ScannerResult;
import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.repositories.ScannerResultRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.HttpURLConnection;

@RunWith(SpringJUnit4ClassRunner.class) // 引入Spring-Test框架支持
@ContextConfiguration(locations = "classpath:jdbc-test.xml")
//@SpringApplicationConfiguration(classes = DemoApplication.class) // 指定SpringBoot-Application启动类
//@WebAppConfiguration // Web项目，Junit需要模拟ServletContext，测试类加上@WebAppConfiguration。
public class JPATest extends AbstractJUnit4SpringContextTests {

    @Autowired
    ScannerResultRepository scannerResultRepository;

    @Test
    public void testJpa(){
        /*scannerResultRepository.
                save(new ScannerResult(5000L, false, false, 4, 4, new ScannerTask("20.30.1.10", 8080)));
        ScannerResult result = scannerResultRepository.findOne(1L);*/
    }


}
