package com.server.therad.handlefile;

import com.server.logback.LogBackConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author CYX
 * @create 2018-06-20-23:43
 */
public class CreateFileThreadApp01 {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateFileThreadApp01.class);

    //logback配置文件
    private static final String LOGBACK_FILE_PATH = "./conf/logback/logback.xml";

    //输出文件的根目录
    private static final String DIR_FILE_PATH = "./output/";

    //key标识-原子操作-用于构造文件名
    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {

        //初始化日志
        try {
            LogBackConfigLoader.load(LOGBACK_FILE_PATH);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        //根目录创建
        File direFile = new File(DIR_FILE_PATH);
        if (!direFile.exists() || !direFile.isDirectory()) {
            direFile.mkdirs();
        }

        for (int i = 0; i < 10; i++) {
            CreateFileThreadHandler01 handler01 = new CreateFileThreadHandler01();
            Thread thread = new Thread(handler01);
            thread.start();

            CreateFileThreadHandler01 handler02 = new CreateFileThreadHandler01();
            Thread thread2 = new Thread(handler02);
            thread2.start();

            CreateFileThreadHandler01 handler03 = new CreateFileThreadHandler01();
            Thread thread3 = new Thread(handler03);
            thread3.start();
        }


    }

}
