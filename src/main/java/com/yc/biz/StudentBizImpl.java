package com.yc.biz;

import com.yc.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-04 14:27
 */

@Service
public class StudentBizImpl{

    private StudentDao studentDao;

    public StudentBizImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public StudentBizImpl() {

    }

    //@Inject       //  javax中的依赖注入,如果有多个对象，比如这里（ StudentDaoJpaImpl和StudentDaoMybatisImpl对象）
                    //因为有多个对象可以注入，所以这里必须用@Named("studentDaoJpaImpl")来约定,如果只有一个对象则可以不写

    @Autowired      //  spring定义(org.springframework
    @Qualifier("studentDaoJpaImpl")     //如果有多个对象，比如这里（ StudentDaoJpaImpl和StudentDaoMybatisImpl对象）
    //因为有多个对象可以注入，所以这里必须用@Named("studentDaoJpaImpl")来约定

   // @Resource(name = "studentDaoJpaImpl")    //按名字装配
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
