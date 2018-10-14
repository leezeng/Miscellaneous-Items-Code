package com.server.spring.spring01;

import java.util.List;

/**
 * @author CYX
 * @create 2018-04-30-8:34
 */
public class SpellChecker {

    private String name;
    private String address;
    private List<String> infos;


    public SpellChecker(String name, String address, List<String> infos) {
        this.name = name;
        this.address = address;
        this.infos = infos;
    }

    public SpellChecker(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public SpellChecker() {
        System.out.println("SpellChecker 初始化");
    }

    public void checkSpelling() {
        System.out.println("执行checkSpelling方法.");
        System.out.println("name : " + name + " , address : " + address);
        System.out.println("infos : " + infos);
    }

}
