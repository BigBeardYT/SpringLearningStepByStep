package com.yc.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-05 11:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface MyComponentScan {

    String[] basePackages() default {};
}

