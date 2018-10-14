package com.server.spring.spring08;

/**
 * @author CYX
 * @create 2018-04-30-22:06
 */
public class HelloWorldImpl1 implements HelloWorld {

    @Override
    public void printHelloWorld() {
        System.out.println("HelloWorldImpl-1.printHelloWorld()");
    }

    @Override
    public void doPrint() {
        System.out.println("HelloWorldImpl-1.doPrint");
    }
}
