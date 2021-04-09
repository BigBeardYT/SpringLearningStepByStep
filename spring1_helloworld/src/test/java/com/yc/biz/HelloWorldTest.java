package com.yc.biz;

import com.yc.AppConfig;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-04 15:15
 */
public class HelloWorldTest extends TestCase {  //测试用例

    private ApplicationContext ac;  //spring 容器

    @Override
    @Before
    public  void setUp(){
        ac = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void testHello(){
        HelloWorld hw = (HelloWorld) ac.getBean("helloWorld");
        hw.Hello();

        HelloWorld hw1 = (HelloWorld)ac.getBean("helloWorld");
        hw1.Hello();
    }


}
