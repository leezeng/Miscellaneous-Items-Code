package com.server.other.other_01;

/**
 * @author CYX
 * @create 2018-07-08-18:46
 */
public class HelloB extends HelloA {

    public HelloB() {
        System.out.println("HelloB");
    }

    {
        System.out.println("I'm B class");
    }

    static {
        System.out.println("static B");
    }

    public static void main(String[] args) {
        //new HelloB();

        System.out.println("-------main start-------");
        new HelloB();
        new HelloB();
        System.out.println("-------main end-------");

    }
}
