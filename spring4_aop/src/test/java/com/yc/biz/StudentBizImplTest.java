package com.yc.biz;

import com.yc.MyAppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration( classes = MyAppConfig.class )
public class StudentBizImplTest {

//    @Resource(name = "studentBizImpl")
//    private StudentBizImpl sbi;
    @Autowired
    private StudentBiz sbi;

    @Test
    public void add() {

        sbi.add("张三");
    }
    @Test
    public void update() {
        sbi.update("张三");
    }
    @Test
    public void find() {
        sbi.find("张三");
    }
}