package com.server.spring.spring08;

/**
 * @author CYX
 * @create 2018-04-30-22:08
 */
public class HelloWorldImpl2 implements HelloWorld {

    @Override
    public void printHelloWorld() {
        System.out.println("HelloWorldImpl-2.printHelloWorld()");
    }

    @Override
    public void doPrint() {
        System.out.println("HelloWorldImpl-2.doPrint");
    }
}
