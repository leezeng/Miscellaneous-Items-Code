package com.server;

import com.server.conf.LoadConfig;
import com.server.thread.ThreadRealTimeNews;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;

/**
 * @author CYX
 */
public class AppHttpClientGetNewsData {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppHttpClientGetNewsData.class);

    public static void main(String[] args) {

        try {

            String fileName = "./conf/logback/logback-config.xml";

            LoadConfig.getInstance().loadLogBack(fileName);

            LoadConfig.getInstance().loadConfigProperties();

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        try {

            //实时新闻线程获取-解析-定时器
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new ThreadRealTimeNews(), 0, 2000);

            //测试使用
            //Thread thread = new Thread(new ThreadRealTimeNews());
            //thread.start();

            //从数据库中取出所有context为空的数据，根据url字段从网上爬取新闻正文，然后更新到数据库中
            //HandlerNews.loadNewsByDatabase();
            //
            //Thread threadGetNewsDetails = new Thread(new ThreadGetNewsDetails());
            //threadGetNewsDetails.start();

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
