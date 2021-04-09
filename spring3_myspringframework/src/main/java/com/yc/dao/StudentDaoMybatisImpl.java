package com.yc.dao;

import java.util.Random;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-04 14:28
 */

//@MyRepository     //  异常的转化   从Exception转为RuntimeException
public class StudentDaoMybatisImpl implements StudentDao{


    @Override
    public int add(String name) {
        System.out.println("mybatis 添加学生:"+ name);
        Random r = new Random();
        return r.nextInt();
    }

    @Override
    public void update(String name) {
        System.out.println("mybatis 更新学生信息:"+name);
    }
}
