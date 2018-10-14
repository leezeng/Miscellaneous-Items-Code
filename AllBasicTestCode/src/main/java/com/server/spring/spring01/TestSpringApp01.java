package com.server.spring.spring01;

import com.server.spring.interfaces.People;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Hello world!
 */
public class TestSpringApp01 {

    public static void main(String[] args) throws Exception {

        //ApplicationContext context = new ClassPathXmlApplicationContext("./conf/spring/spring01/applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("./conf/spring/spring01/applicationContext.xml");


        People propleBean = (People) context.getBean("cyxPeople");

        propleBean.sayName();
        propleBean.spellCheck();

    }
}
