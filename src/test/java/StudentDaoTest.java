import com.yc.biz.StudentBizImpl;
import com.yc.dao.StudentDao;
import com.yc.dao.StudentDaoMybatisImpl;
import junit.framework.TestCase;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-04 14:19
 */
public class StudentDaoTest extends TestCase {

    private StudentDao studentDao;
    private StudentBizImpl studentBizImpl;

    @Override
    public void setUp() throws  Exception{
        //1.能否自动完成 实例化对象 -> IOC 控制 反转  ->  由尤其实例化对象 ，由容器来完成

        //studentDao = new StudentDaoJpaImpl();

       studentDao =  new StudentDaoMybatisImpl();    //IOC

        //2.能否自动完成 装配过程  ->  DI 依赖注入  ->  由容器装配对象
        studentBizImpl = new StudentBizImpl();
        studentBizImpl.setStudentDao(studentDao);

    }

    public void testAdd(){
        studentDao.add("张三");
    }

    public void testUpate(){
        studentDao.update("李四");
    }

    public void testBizAdd(){
        studentBizImpl.add("王五");
        studentBizImpl.update("赵六");
    }
}
