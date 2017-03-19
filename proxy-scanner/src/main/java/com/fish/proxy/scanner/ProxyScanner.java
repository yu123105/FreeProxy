package com.fish.proxy.scanner;

import com.fish.proxy.scanner.component.ScannerTaskExecutor;
import com.fish.proxy.scanner.component.impl.AIOScannerTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


//@EnableAutoConfiguration
@Component
@ComponentScan
public class ProxyScanner{

    @Autowired
    private ScannerTaskExecutor aIOScannerTaskExecutor;

    public ProxyScanner(){

    }

    public void start(){
        System.out.println("11111111111111111111111111111111111111111111111111111");
        //aIOScannerTaskExecutor.executeTaskWithFactory();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ProxyScanner.class);
        ProxyScanner proxyScanner = ctx.getBean(ProxyScanner.class);
        proxyScanner.start();

    }
}
