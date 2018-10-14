package com.server.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 在配置文件中配置初始值，通过映射自动加载
 * <p>
 * 设置配置文件的路径，并设定字符集
 * <p>
 * 通过@ConfigurationProperties加载properties文件内的配置
 * 通过prefix属性指定properties配置的前缀
 * <p>
 * Component,将普通POJO实例化到Spring容器中
 *
 * @author CYX
 * @create 2018-07-02-19:44
 */
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "person")
@Component
public class PersonVO {

    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "PersonVO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
