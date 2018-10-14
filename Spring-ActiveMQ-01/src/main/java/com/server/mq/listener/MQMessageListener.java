package com.server.mq.listener;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * MQ监听器-异步接收
 *
 * @author CYX
 * @create 2018-04-29-20:40
 */
public class MQMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        try {

            System.out.println(message);

            BytesMessage bytesMsg = (BytesMessage) message;
            byte[] bytes = new byte[(int) bytesMsg.getBodyLength()];
            bytesMsg.readBytes(bytes);
            System.out.println("Received byte message: " + new String(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
