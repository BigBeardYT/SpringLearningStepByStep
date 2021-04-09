package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-05 09:17
 */
@Configuration
@ComponentScan(basePackages = {"com.huwei","com.mimi"})
public class AppConfig {

    @Bean //beanid:"r"
    public Random r(){
        return new Random();
    }

//    @Bean
//    public Person p1(){
//        return new Person( "张三", 1.70, 80);
//    }
}
