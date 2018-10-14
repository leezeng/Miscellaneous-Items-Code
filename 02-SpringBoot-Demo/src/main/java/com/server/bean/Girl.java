package com.server.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author CYX
 * @create 2018-07-10-18:55
 */

@Entity
@Table(name = "girl")
public class Girl extends Pagination {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "cup_size")
    private String cupSize;

    @Column(name = "age")
    private Integer age;

    public Girl() {
    }

    @Override
    public String toString() {
        return "Girl{" +
                "id=" + id +
                ", cupSize='" + cupSize + '\'' +
                ", age=" + age +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCupSize() {
        return cupSize;
    }

    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
