package com.server.spring.spring08;

/**
 * logger-横切关注点
 *
 * @author CYX
 * @create 2018-05-01-12:21
 */
public class LogHandler {

    public void LogBefore() {
        System.out.println("横切关注点---Logger 前");
    }

    public void LogAfter() {
        System.out.println("横切关注点---Logger 后");
    }

}
