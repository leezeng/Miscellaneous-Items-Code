package com.server.therad.handlefile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CYX
 * @create 2018-06-20-23:41
 */
public class CreateFileThreadHandler01 implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateFileThreadHandler01.class);

    @Override
    public void run() {

        int number = CreateFileThreadApp01.atomicInteger.incrementAndGet();
        StringBuilder stringBuilder = new StringBuilder();

        String fileName = stringBuilder.append(number).append("-").append(System.nanoTime()).append(".txt").toString();
        LOGGER.info(fileName);


    }
}
