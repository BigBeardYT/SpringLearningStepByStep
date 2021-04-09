package com.yc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-04 15:13
 */
@Configuration     //表示当前的类是一个配置类
@ComponentScan(basePackages = "com.yc") //将来要托管的bean要扫描
public class AppConfig {    //Java容器的配置

}
