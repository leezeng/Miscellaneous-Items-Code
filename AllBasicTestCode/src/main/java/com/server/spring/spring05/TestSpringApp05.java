package com.server.spring.spring05;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 测试组件扫描
 *
 * @author CYX
 * @create 2018-04-17-21:08
 */
public class TestSpringApp05 {


    public static void main(String[] args) {

        //ApplicationContext context = new ClassPathXmlApplicationContext("/spring/test05/applicationContext05.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("./conf/spring/spring05/applicationContext05.xml");


        SgtPeppers sgtPeppers = context.getBean("sssgtPeppers", SgtPeppers.class);

        sgtPeppers.play();

    }

}
