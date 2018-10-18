package com.server;

import com.context.ApplicationContext;
import com.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * ClassPathXmlApplicationContext测试-debug
 *
 * @author CYX
 * @create 2018-10-18-12:18
 */
public class TestClassPathXmlApplicationContext {

    public static void main(String[] args) {

        String applicationClassfile = "./conf/spring/applicationContext01.xml";

        try {
            ApplicationContext context = new ClassPathXmlApplicationContext(applicationClassfile);
            //ApplicationContext context = new FileSystemXmlApplicationContext(applicationClassfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
