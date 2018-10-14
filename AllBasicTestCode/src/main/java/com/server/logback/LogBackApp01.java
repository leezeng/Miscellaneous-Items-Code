package com.server.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CYX
 * @create 2018-06-11-20:42
 */
public class LogBackApp01 {

    private static final Logger logger = LoggerFactory.getLogger(LogBackApp01.class);

    public static void main(String[] args) throws Exception {

        String fileName = "./conf/logback/logback.xml";
        LogBackConfigLoader.load(fileName);

        //如果在解析配置文件期间发生警告或错误，logback将自动在控制台上打印内部状态消息
        //LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        //StatusPrinter.print(loggerContext);

        logger.info("Hello");

    }

}
