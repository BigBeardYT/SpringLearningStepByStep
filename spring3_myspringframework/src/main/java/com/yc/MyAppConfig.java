package com.yc;

import com.yc.springframework.stereotype.MyComponentScan;
import com.yc.springframework.stereotype.MyConfiguration;

@MyConfiguration
@MyComponentScan(basePackages = {"com.yc.dao", "com.yc.biz"})
public class MyAppConfig {

//    @MyBean
//    public HelloWorld hw() {      //method.invoke(  MyAppConfig对象 , xxx  )
//        return new HelloWorld();
//    }


}
