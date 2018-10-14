package com.server.spring.spring04;


import org.springframework.stereotype.Component;

/**
 * @author CYX
 * @create 2018-04-17-20:47
 */
//该注解表明该类会作为组件类，并告知Spring要为这个类创建bean。
@Component("sgtPeppers")
//@Component("sssgtPeppers")
//@Named("test4-sgtPeppers")
public class SgtPeppers implements CompactDisc {

    private String title = "Sgt. Pepper's Lonely Hearts Club Band";
    private String artist = "The Beatles";

    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + artist);
    }
}
