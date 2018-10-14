package com.server.spring.spring02;


import com.server.spring.interfaces.People;

/**
 * @author CYX
 * @create 2018-04-16-22:24
 */
public class OnePeople implements People {

    private String name;
    private String age;
    private String address;

    @Override
    public void spellCheck() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void sayName() {
        System.out.println("OnePeople{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", address='" + address + '\'' +
                '}');
    }

}
