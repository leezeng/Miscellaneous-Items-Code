package com.server.spring.spring07;

/**
 * @author CYX
 * @create 2018-04-30-21:37
 */
public class HelloWorld {

    private String message;


    public void getMessage() {
        System.out.println("the message : " + message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
