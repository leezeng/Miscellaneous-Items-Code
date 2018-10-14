package com.server.mq.consumer;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * ActiveMQ-消息消费者-同步
 *
 * @author CYX
 * @create 2018-04-29-20:00
 */
public class MQConsumerSynchronize {

    private static JmsTemplate jt = null;

    public static void main(String[] args) throws Exception {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-synchronize.xml");

        //获取JmsTemplate对象
        jt = (JmsTemplate) ctx.getBean("jmsTemplate");

        //接收消息
        Object obj = jt.receive();

        ActiveMQBytesMessage activeMQBytesMessage = (ActiveMQBytesMessage) obj;

        System.out.println(new String(activeMQBytesMessage.getContent().getData()));

    }

}
