package com.server.spring.spring02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Spring读取properties配置文件，占位符指向的配置信息放在bean中定义的工具
 *
 * @author CYX
 * @create 2018-04-16-22:23
 */
public class TestSpringApp02 {

    public static void main(String[] args) {

        //ApplicationContext context = new ClassPathXmlApplicationContext("./conf/spring/test2/applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("./conf/spring/spring02/applicationContext.xml");

        OnePeople onePeople = context.getBean("onePeople", OnePeople.class);
        onePeople.sayName();

    }

}
