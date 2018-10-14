package com.server.thread;

import com.server.mapper.RealTimeNewsMapper;
import com.server.po.RealTimeNewsDetailsPO;
import com.server.service.HTTPClientHandler;
import com.server.service.HandlerNews;
import com.server.util.mybatis.SqlSessionFactoryUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取新闻详情
 *
 * @author CYX
 * @create 2018-07-17-19:54
 */
public class ThreadGetNewsDetails implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadGetNewsDetails.class);

    private HTTPClientHandler clientHandler = new HTTPClientHandler();

    @Override
    public void run() {

        String htmlStr;
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();

        //从队列中获取数据，不为空，就一直循环
        while (HandlerNews.arrayBlockingQueue.size() > 0) {

            //从队列中获取一个新闻简介
            RealTimeNewsDetailsPO detailsPO = HandlerNews.arrayBlockingQueue.poll();

            try {
                // 以get方式请求该URL
                CloseableHttpResponse response = clientHandler.getHTTPClientNewsDetails(detailsPO.getUrl());

                // 返回码
                int resStatu = response.getStatusLine().getStatusCode();
                // 200正常
                if (resStatu == HttpStatus.SC_OK) {
                    // 获得相应实体
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        htmlStr = EntityUtils.toString(entity, "UTF-8");
                        htmlStr = htmlStr.replace("&nbsp;", " ");
                        LOGGER.info("htmlStr : {}", new Object[]{htmlStr});
                        HandlerNews.analysisNewsInfo(htmlStr, detailsPO);

                        RealTimeNewsMapper realTimeNewsMapper = sqlSession.getMapper(RealTimeNewsMapper.class);
                        int result = realTimeNewsMapper.updateNewsToRealTimeNewsInfo(detailsPO);
                        LOGGER.info("更新成功：" + result);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    sqlSession.commit();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }
}

