package com.yc.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-09 19:34
 */
@Aspect //  切面类： 要增强的功能写到这里 (除了这个注解还要IOC托管的注解)
@Component  //IOC注解，以实现让spring托管的功能
public class LogAspect {
    //切入点的声明  point segnate
    @Pointcut("execution(* com.yc.biz.StudentBizImpl.add*(..))")       //切入点表达式：哪些方法上加增强
    private void add(){

    }

    @Pointcut("execution(* com.yc.biz.StudentBizImpl.update*(..))")       //切入点表达式：哪些方法上加增强
    private void update(){

    }

    @Pointcut("add() || update()")
    public void addAndUpdate(){

    }

    //切入点表达式的语法: ? 代表出现0次或一次
    //modifiers-pattern:修饰符
    //ret-type-pattern:返回类型
    //declaring-type-pattern:
    //name-pattern:名字模型
    //throws-pattern:
    //execution(modifiers-pattern?ret-type-pattern?declaring-type-name-pattern()
    //


    //增加的声明
    @Before("com.yc.aspect.LogAspect.addAndUpdate()")
    public void doAccessCheck(){
        System.out.println("==========前置增强的日志===================");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dstr = sdf.format( d );
        System.out.println("执行时间为:"+dstr);
        System.out.println("==========前置增强的日志结束===================");

    }
}
