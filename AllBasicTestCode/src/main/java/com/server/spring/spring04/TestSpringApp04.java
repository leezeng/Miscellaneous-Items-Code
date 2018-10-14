package com.server.spring.spring04;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * 测试组件扫描
 *
 * @author CYX
 * @create 2018-04-17-21:08
 */
public class TestSpringApp04 {


    public static void main(String[] args) {

        //ApplicationContext context = new ClassPathXmlApplicationContext("/spring/test4/applicationContext.xml");

        //ApplicationContext context = new FileSystemXmlApplicationContext("./conf/spring/spring04/applicationContext.xml");


        ApplicationContext context =new AnnotationConfigApplicationContext(CDPlayerConfig.class);
        SgtPeppers sgtPeppers = context.getBean("sgtPeppers",SgtPeppers.class);

        sgtPeppers.play();

    }

}
