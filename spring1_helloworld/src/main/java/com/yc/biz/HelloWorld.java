package com.yc.biz;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-04 15:04
 */

@Component      //只要加了这个注解，则这个类就可以被spring托管
@Lazy
public class HelloWorld {       //创建这个类的对象

    public HelloWorld(){
        System.out.println("无参构造方法");
    }

    public void Hello(){
        System.out.println("Hello world");
    }
}
