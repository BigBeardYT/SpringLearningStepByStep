package com.yc.biz;

import com.yc.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-04 14:27
 */

@Service
public class StudentBizImpl implements StudentBiz {

    private StudentDao studentDao;

    public StudentBizImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public StudentBizImpl() {

    }


    @Autowired
    @Qualifier("studentDaoJpaImpl")    //按名字装配
    public void setStudentDao(StudentDao studentDao){
        this.studentDao = studentDao;
    }


    @Override
    public int add(String name) {
        System.out.println("=============业务层===============");
        System.out.println("用户名名字是否重合");
        int result = studentDao.add(name);
        System.out.println("-------------业务层操作结束-------------");
        return result;
    }

    @Override
    public void update(String name) {
        System.out.println("=============业务层===============");
        System.out.println("更新用户名");
        studentDao.update(name);
        System.out.println("-------------业务层操作结束-------------");
    }

    @Override
    public void find(String name){
        System.out.println("=============业务层===============");
        System.out.println("业务层查找名字:"+name);
        studentDao.find(name);
        Random random = new Random();
        try {
            Thread.sleep(10*random.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------------业务层操作结束-------------");
    }

}
