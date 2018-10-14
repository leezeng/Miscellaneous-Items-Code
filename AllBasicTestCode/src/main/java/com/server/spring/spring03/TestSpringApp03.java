package com.server.spring.spring03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Spring加载properties，获取指定key的value
 *
 * @author CYX
 * @create 2018-04-16-22:39
 */
public class TestSpringApp03 {

    public static void main(String[] args) {

        //ApplicationContext context = new ClassPathXmlApplicationContext("spring/test3/applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("./conf/spring/spring03/applicationContext.xml");


        System.out.println(CustomizedPropertyConfigurer.getCtxPro("test3.name"));
        System.out.println(CustomizedPropertyConfigurer.getCtxPro("test3.age"));
        System.out.println(CustomizedPropertyConfigurer.getCtxPro("test3.address"));
        System.out.println(CustomizedPropertyConfigurer.getCtxPro("test.name"));
        System.out.println(CustomizedPropertyConfigurer.getCtxPro("test.age"));
        System.out.println(CustomizedPropertyConfigurer.getCtxPro("test.address"));

    }

}
