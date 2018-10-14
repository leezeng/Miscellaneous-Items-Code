package com.server.mq.consumer;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * 异步消费者
 *
 * @author CYX
 * @create 2018-04-29-20:52
 */
public class MQConsumerAsynchronous {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationcontext-asynchronous.xml");


    }

}
