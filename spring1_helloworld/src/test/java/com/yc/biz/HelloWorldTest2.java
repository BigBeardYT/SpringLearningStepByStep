package com.yc.biz;

import com.yc.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-04 15:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@DependsOn("helloWorld")
public class HelloWorldTest2{  //测试用例

    @Autowired  //调用构造方法，即使是懒加载
    private HelloWorld hw;  //默认情况下，所有的bean都是eager加载（勤快加载）

    @Test
    public void testHello(){
        System.out.println("aaa");
    }


}
