package com.server.vo;

/**
 * @author CYX
 * @create 2018-05-31-20:03
 */
public class PeopleVO {

    private String name;
    private String age;
    private String address;
    private String six;

    @Override
    public String toString() {
        return "PeopleVO{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", address='" + address + '\'' +
                ", six='" + six + '\'' +
                '}';
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

    public String getSix() {
        return six;
    }

    public void setSix(String six) {
        this.six = six;
    }
}
