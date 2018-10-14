package com.server.service;

import com.server.constant.CommonsConstant;
import com.server.mapper.RealTimeNewsMapper;
import com.server.po.RealTimeNewsDetailsPO;
import com.server.util.mybatis.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 加载数据库中的信息
 *
 * @author CYX
 * @create 2018-07-17-20:42
 */
public class HandlerNews {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerNews.class);

    public static ArrayBlockingQueue<RealTimeNewsDetailsPO> arrayBlockingQueue = null;

    private static final ReentrantLock lock = new ReentrantLock();

    /**
     * 从数据库中加载所有content值为空的数据，存入队列中
     */
    public static void loadNewsByDatabase() throws Exception {

        arrayBlockingQueue = new ArrayBlockingQueue<RealTimeNewsDetailsPO>(200000);
        String content = "";
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        RealTimeNewsMapper realTimeNewsMapper = sqlSession.getMapper(RealTimeNewsMapper.class);
        List<RealTimeNewsDetailsPO> detailsPOList = realTimeNewsMapper.findAllNewsWhereByContent(content);
        arrayBlockingQueue.addAll(detailsPOList);
        LOGGER.info("arrayBlockingQueue size : {}", new Object[]{arrayBlockingQueue.size()});
    }


    /**
     * 解析HTML,获取新闻内容
     *
     * @param htmlStr
     * @param detailsPO
     */
    public static void analysisNewsInfo(String htmlStr, RealTimeNewsDetailsPO detailsPO) {

        lock.lock();
        try {

            int timeStartIndex = htmlStr.indexOf("<time>");
            int timeEndIndex = htmlStr.indexOf("</time>");
            String time = htmlStr.substring(timeStartIndex - 1, timeEndIndex).replaceAll(CommonsConstant.HTML_REGULAR_EXPRESSION, "");
            LOGGER.info("time : {}", new Object[]{time});

            int articleStartIndex = htmlStr.indexOf("<article");
            int articleEndIndex = htmlStr.indexOf("</article>");
            String newInfo = htmlStr.substring(articleStartIndex, articleEndIndex).replaceAll(CommonsConstant.HTML_REGULAR_EXPRESSION, "").trim();
            LOGGER.info("newInfo : {}", new Object[]{newInfo});

            detailsPO.setGmt_publish(time);
            detailsPO.setContent(newInfo);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            lock.unlock();
        }

    }

}
