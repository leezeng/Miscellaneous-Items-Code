package com.server.spring.spring08;

/**
 * 横切关注点
 *
 * @author CYX
 * @create 2018-04-30-22:08
 */
public class TimeHandler {

    public void printTime() {
        System.out.println("横切关注点---time : " + System.currentTimeMillis());
    }

}
