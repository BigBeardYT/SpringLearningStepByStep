package com.yc.springframework.context;

import com.yc.springframework.stereotype.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @program: testSpring
 * @description:
 * @author: Yt
 * @create: 2021-04-05 11:41
 */
public class MyAnnotationConfigApplicationContext implements MyApplicationContext{
    private Map<String,Object> beanMap = new HashMap<String,Object>();
    //BeanDefinition
    //private Map<String,Class> classMap = new HashMap<String,Class>();

    public MyAnnotationConfigApplicationContext(Class<?>... componentClasses)  {
        try {
            register(componentClasses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void register(Class<?>[] componentClasses) throws Exception {
        if(componentClasses == null || componentClasses.length<=0){
            throw  new RuntimeException("没有指定配置类");
        }
        for(Class cl:componentClasses) {
            //请实现这里面的代码
            //源码1：  只实现IOC， MyPostConstruct  MyPreDestroy
            if(!cl.isAnnotationPresent(MyConfiguration.class)){
                continue;
            }
            String[] basePackages = getAppConfigBasePackages(cl);
            if(cl.isAnnotationPresent(MyComponentScan.class) ){
                MyComponentScan mcs = (MyComponentScan) cl.getAnnotation( MyComponentScan.class );
                if(mcs.basePackages()!=null&&mcs.basePackages().length>0){
                    basePackages = mcs.basePackages();
                }
            }
            //处理@MyBean的情况
            Object obj = cl.newInstance();  //obj就是当前解析的 MyAppConfig对象
            handleAtMyBean(cl,obj);
            //处理  basePackages  基础包下的所有的托管bean
            for( String basePackage:basePackages){
                System.out.println("扫描包路径:"+basePackage);
                scanPackageAndSubPackageClasses(  basePackage  );
            }
            //继续其它托管bean
            handleMenagedBean();
            //版本2： 循环  beanMap中的每个bean,找到它们每个类中的每个由@Autowired @Resource注解的方法以实现di,
            handleDi(beanMap);
        }
    }

    /*
    循环beanMap 中的每个bean，找到它们每个类中的每个@Autowired @Resource注解
     */
    private void handleDi(Map<String, Object> beanMap) throws InvocationTargetException, IllegalAccessException {
        Collection<Object> objectCollection = beanMap.values();
        for(Object obj : objectCollection){
            Class cls = obj.getClass();
            Method[] ms = cls.getDeclaredMethods();
            for(Method m:ms){
                if(m.isAnnotationPresent(MyAutowired.class)&&m.getName().startsWith("set")){
                    invokeAutowiredMethod( m,obj );//激活自动装配的方法
                }else if(m.isAnnotationPresent(MyResource.class)&&m.getName().startsWith("set")){
                    invokeResourceMethod( m,obj );//激活自动装配的方法
                }
            }
            Field[] fs = cls.getDeclaredFields();
            for(Field field:fs){
                if(field.isAnnotationPresent(MyAutowired.class)){

                }else if(field.isAnnotationPresent(MyResource.class)){

                }
            }
        }
    }

    /**
     *
     * @param m
     * @param obj
     */
    private void invokeResourceMethod(Method m, Object obj) throws InvocationTargetException, IllegalAccessException {
        //1.取出  MyResourse中的name属性值，当成beanId
        MyResource mr = m.getAnnotation(  MyResource.class );
        String beanId = mr.name();
        //2.如果没有 则取出m方法中参数的类型名，改成 首字母小写
        if(beanId==null || beanId.equalsIgnoreCase("")){
            String pname = m.getParameterTypes()[0].getSimpleName();   //SimpleName简单名，取短路径,省去了例如com.yc.....sldfjskld
            beanId = pname.substring(0,1).toLowerCase()+pname.substring(1);
        }
        //3.从beanMap中取出
        Object o = beanMap.get(beanId);
        //4.invoke
        m.invoke(obj,o);
    }

    /**
     * 激活自动装配的方法
     * @param m
     * @param obj
     */
    private void invokeAutowiredMethod(Method m, Object obj) throws InvocationTargetException, IllegalAccessException {
        //  思路：
        //1.取出 m的参数类型
        Class typeClass = m.getParameterTypes()[0];
        //2.从beanMap中循环所有的object
        Set<String> keys = beanMap.keySet();
        for(String key:keys){
            //3.如果是， 则从beanMap中取出
            Object o = beanMap.get(key);
            //4,判断 这些object 是否为 参数类型的实例 instanceof
            Class [] intefaces = o.getClass().getInterfaces();
            for(Class c:intefaces){
                System.out.println(c.getName()+"\t"+typeClass);
                if( c == typeClass ){
                    //5.invoke
                    m.invoke(obj,o);
                    break;
                }
            }
        }
    }

    private void handleMenagedBean() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for( Class c:managedBeanClasses){
            if(c.isAnnotationPresent(MyComponent.class)){
               savaManagedBean(c);
            }else if( c.isAnnotationPresent(MyService.class)){
                savaManagedBean(c);
            }else if( c.isAnnotationPresent(MyRepository.class)){
                savaManagedBean(c);
            }else if( c.isAnnotationPresent(MyController.class)){
                savaManagedBean(c);
            }else {
                continue;
            }
        }
    }

    /**
     * 存
     * @param c
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void savaManagedBean(  Class c) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o = c.newInstance();
        handlePostConstruct(o,c);
        String beanId = c.getSimpleName().substring(0,1).toLowerCase()+c.getSimpleName().substring(1);
        beanMap.put(beanId,o);
    }

    /**
     *
     * @param basePackage
     */
    private void scanPackageAndSubPackageClasses(String basePackage) throws IOException, ClassNotFoundException {
        String packagePath = basePackage.replaceAll("\\.","/");
        System.out.println("扫描包路径:"+basePackage+"替换后:"+packagePath);    //com.yc.bean
        Enumeration<URL> files = Thread.currentThread().getContextClassLoader().getResources(packagePath);
        while(  files.hasMoreElements()){
            URL url = files.nextElement();
            System.out.println("配置的扫描路径为:"+url.getFile());
            //TODO:递归这些目录，查找.class文件
            findClassesInPackages(  url.getFile(),basePackage );        //第二个参数：com.yc.bean
        }
    }

    private Set<Class> managedBeanClasses = new HashSet<Class>();

    /**
     * 查找  file 下面及子包所有的要托管的class，存到set(managedBeanClasses)（避免重复）
     * @param file
     * @param basePackage
     */
    private void findClassesInPackages(String file, String basePackage) throws ClassNotFoundException {
        File f = new File(  file  );
        File[] classFiles = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".class")||file.isDirectory();
            }
        });

        for(File cf:classFiles){
            if(cf.isDirectory()){
                //ruguo是目录，则递归
                //凭借子目录
                basePackage+="."+cf.getName().substring( cf.getName().lastIndexOf("/")+1);
                findClassesInPackages(cf.getAbsolutePath(),basePackage);
            }else{
                //加载  cf  作为 class 文件
                URL[] urls = new URL[]{};
                URLClassLoader url  = new URLClassLoader(urls);
                Class c = url.loadClass(  basePackage+"."+cf.getName().replace(".class","" ));         //com.yc.bean.Htllo.class
                //manageBeanCLasses.add()
            }
        }
    }

    /**
     * 处理MyAppConfig配置类中的@MyBean注解，完成IOC操作
     * @param cls
     * @param obj
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void handleAtMyBean( Class cls,Object obj) throws InvocationTargetException, IllegalAccessException {
        //1.获取cls中共所有的method
        Method[] ms = cls.getDeclaredMethods();
        //2.循环 判断 每个method上是否有MyBean注解
        for(Method m:ms){
            if(m.isAnnotationPresent(MyBean.class)){
                //3.有 则invoke它，它有返回值，将返回值存到  beanMap ，键是方法名 ，值 是返回值 对象
                Object o = m.invoke(obj);
                //TODO:加入处理 MyBean注解对应的方法所实例化的类中的MyPostConstruct
                handlePostConstruct(o,o.getClass());    //o在这里指 HelloWorld对象  o.getClass()它的反射对象
                beanMap.put( m.getName(),o);
            }
        }

    }

    /**
     * 处理一个Bean中的@MyPostConstruct对应的方法
     * @param o
     * @param cls
     */
    private void handlePostConstruct(Object o, Class<?> cls) throws InvocationTargetException, IllegalAccessException {
        Method [] ms = cls.getDeclaredMethods();
        for(Method m: ms){
            if(  m.isAnnotationPresent((MyPostConstruct.class))){
                m.invoke(o);
            }
        }
    }

    /**
     * 取出当前  AppConfig类所在的包路径
     * @return
     * @param cl
     */
    private String[] getAppConfigBasePackages(Class cl) {
        String [] paths = new String [1];
        paths[0] = cl.getPackage().getName();
        return paths;
    }


    @Override
    public Object getBean(String id){
        System.out.println(beanMap);
        return beanMap.get(id);
    }
}
