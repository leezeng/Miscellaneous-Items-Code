package com.server.spring.spring06;


import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author CYX
 * @create 2018-04-17-20:47
 */
public class SgtPeppers implements CompactDisc {

    private String title = "Sgt. Pepper's Lonely Hearts Club Band";
    private String artist = "The Beatles";

    @Autowired
    private SayHello sayHello;


    public SgtPeppers() {
    }

    public SgtPeppers(SayHello sayHello) {
        this.sayHello = sayHello;
    }

    public SgtPeppers(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + artist);
        sayHello.sayHelloWithAddress();
        sayHello.sayHelloWithName();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
