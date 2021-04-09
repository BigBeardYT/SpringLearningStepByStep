package com.yc.biz;

import com.yc.dao.StudentDao;
import com.yc.springframework.stereotype.MyResource;
import com.yc.springframework.stereotype.MyService;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-04 14:27
 */

@MyService
public class StudentBizImpl{

    private StudentDao studentDao;

    public StudentBizImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public StudentBizImpl() {

    }



    @MyResource(name = "studentDaoJpaImpl")    //按名字装配
    public void setStudentDao(StudentDao studentDao){
        this.studentDao = studentDao;
    }


    public int add(String name) {
        System.out.println("=============业务层===============");
        System.out.println("用户名名字是否重合");
        int result = studentDao.add(name);
        System.out.println("============业务层操作结束=========");
        return result;
    }

    public void update(String name) {
        System.out.println("=============业务层===============");
        System.out.println("更新用户名");
        studentDao.update(name);
        System.out.println("============业务层操作结束=========");
    }
}
