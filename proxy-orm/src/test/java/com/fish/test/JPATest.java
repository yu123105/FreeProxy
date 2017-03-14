package com.fish.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class) // 引入Spring-Test框架支持
@ContextConfiguration(locations = "classpath:spring-data-jdbc.xml")
//@SpringApplicationConfiguration(classes = DemoApplication.class) // 指定SpringBoot-Application启动类
//@WebAppConfiguration // Web项目，Junit需要模拟ServletContext，测试类加上@WebAppConfiguration。
public class JPATest extends AbstractJUnit4SpringContextTests {



}
