package com.yc.bean;

import com.yc.springframework.stereotype.MyPostConstruct;
import com.yc.springframework.stereotype.MyPreDestroy;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-05 11:45
 */
//@MyComponent
public class HelloWorld {

    @MyPostConstruct
    public void setup(){
        System.out.println("MyPostConstruct");
    }

    @MyPreDestroy
    public void destroy(){
        System.out.println("MyPredestroy");
    }

    public HelloWorld(){
        System.out.println("hello world 构造");
    }

    public void show(){
        System.out.println("show");
    }



}
