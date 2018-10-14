package com.server.spring.spring01;


import com.server.spring.interfaces.People;

/**
 * @author CYX
 * @create 2018-04-16-7:10
 */
public class CYXPeople implements People {

    private String name;

    private SpellChecker spellChecker;

    public CYXPeople() {
    }

    public CYXPeople(SpellChecker spellChecker) {
        System.out.println("CYXPeople构造器 注入SpellChecker");
        this.spellChecker = spellChecker;
    }

    @Override
    public void spellCheck() {
        spellChecker.checkSpelling();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void sayName() {
        System.out.println("my name : " + name);
    }

}
