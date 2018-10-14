package com.server.spring.spring05;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author CYX
 * @create 2018-04-17-20:47
 */
@Component("sssgtPeppers")
public class SgtPeppers implements CompactDisc {

    private String title = "Sgt. Pepper's Lonely Hearts Club Band";
    private String artist = "The Beatles";

    @Autowired
    private SayHello sayHello;

    /*@Autowired
    public void setSayHello(SayHello sayHello) {
        this.sayHello = sayHello;
    }*/

    /*@Autowired
    public SgtPeppers(SayHello sayHello) {
        this.sayHello = sayHello;
    }*/

    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + artist);
        sayHello.sayHelloWithAddress();
        sayHello.sayHelloWithName();
    }
}
