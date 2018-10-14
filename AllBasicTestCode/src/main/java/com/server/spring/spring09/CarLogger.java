package com.server.spring.spring09;

/**
 * @author CYX
 * @create 2018-05-01-11:19
 */
public class CarLogger {

    public void beforeRun(){
        System.out.println("car is going to run");
    }
    public void afterRun(){
        System.out.println("car is running");
    }

}
