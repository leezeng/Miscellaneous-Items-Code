package com.server.spring.spring06;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试组件扫描
 *
 * @author CYX
 * @create 2018-04-17-21:08
 */
public class TestSpringApp06 {


    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(CDPlayerConfig.class);
        SgtPeppers sgtPeppers = context.getBean("sssgtPeppers", SgtPeppers.class);

        sgtPeppers.play();

    }

}
