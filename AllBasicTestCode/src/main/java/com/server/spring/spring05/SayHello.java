package com.server.spring.spring05;

import org.springframework.stereotype.Component;

/**
 * @author CYX
 * @create 2018-04-30-17:09
 */
@Component
public class SayHello {

    public SayHello() {
        System.out.println("SayHello 默认构造器");
    }

    public void sayHelloWithName() {
        System.out.println("SayHello ：hello cyx");
    }

    public void sayHelloWithAddress() {
        System.out.println("SayHello ：hello 南京");
    }

}
