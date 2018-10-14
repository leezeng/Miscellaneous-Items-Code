package com.server.thread;

import com.server.bean.RealTimeNews;
import com.server.conf.LoadConfig;
import com.server.constant.CommonsConstant;
import com.server.mapper.RealTimeNewsMapper;
import com.server.po.RealTimeNewsDetailsPO;
import com.server.service.HTTPClientHandler;
import com.server.service.HandlerNews;
import com.server.util.JSONUtil;
import com.server.util.MD5Util;
import com.server.util.mybatis.SqlSessionFactoryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.TimerTask;

/**
 * 线程-实时新闻
 *
 * @author CYX
 * @create 2018-07-15-10:22
 */
public class ThreadRealTimeNews extends TimerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadRealTimeNews.class);

    private LoadConfig loadConfig = LoadConfig.getInstance();

    private final String url = "https://api.xinwen.cn/news/all";
    private final String accessKey = loadConfig.getAccessKey();
    private final String secretKey = loadConfig.getSecretKey();

    public static volatile String LAST_ID = null;

    private HTTPClientHandler clientHandler = new HTTPClientHandler();

    @Override
    public void run() {

        SqlSession sqlSession = null;

        try {

            //数据拼接
            //当前时间-13位-毫秒
            String time = String.valueOf(System.currentTimeMillis());
            //生成签名
            StringBuffer clearText = new StringBuffer();
            clearText.append(secretKey).append(time).append(accessKey);
            String signature = MD5Util.stringToMD5(clearText.toString());
            LOGGER.info("signature : " + signature);

            URIBuilder builder = new URIBuilder(url);
            builder.setParameter(CommonsConstant.SIZE, "20");
            builder.setParameter(CommonsConstant.SIGNATURE, signature);
            builder.setParameter(CommonsConstant.TIMESTAMP, time);
            builder.setParameter(CommonsConstant.ACCESS_KEY, accessKey);

            if (StringUtils.isNotEmpty(LAST_ID)) {
                builder.setParameter(CommonsConstant.LAST_ID, LAST_ID);
            }
            LOGGER.info("LAST_ID : " + LAST_ID);

            //调用HTTP-GET
            CloseableHttpResponse response = clientHandler.getHTTPClientRealTimeNewsAcquisition(builder);

            //返回数据处理
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                StringBuffer jsonStr = new StringBuffer(1000);
                jsonStr.append(IOUtils.toString(entity.getContent(), "UTF-8"));
                LOGGER.info("获取数据 : {}", new Object[]{jsonStr});

                RealTimeNews realTimeNews = JSONUtil.toJavaObject(jsonStr.toString(), RealTimeNews.class);
                LOGGER.info("realTimeNews : " + realTimeNews);

                LAST_ID = realTimeNews.getData().getLast_id();

                List<RealTimeNewsDetailsPO> newsDetailsList = realTimeNews.getData().getNews();

                //插入数据库
                insertToDB(sqlSession, newsDetailsList);

            } else {
                LOGGER.info("entity is null");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                sqlSession.commit();
                sqlSession.close();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }


    /**
     * 根据新闻的url，获取新闻详情
     */
    private void getNewsDetails(List<RealTimeNewsDetailsPO> newsDetailsList) {

        for (RealTimeNewsDetailsPO newsDetailsPO : newsDetailsList) {
            String htmlStr;
            try {

                CloseableHttpResponse response = clientHandler.getHTTPClientNewsDetails(newsDetailsPO.getUrl());
                HttpEntity entity = response.getEntity();

                htmlStr = EntityUtils.toString(entity, "UTF-8");
                htmlStr = htmlStr.replace("&nbsp;", " ");
                LOGGER.info("htmlStr : {}", new Object[]{htmlStr});
                HandlerNews.analysisNewsInfo(htmlStr, newsDetailsPO);

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }


    /**
     * 数据插入数据库
     *
     * @param sqlSession
     * @param newsDetailsList
     */
    private void insertToDB(SqlSession sqlSession, List<RealTimeNewsDetailsPO> newsDetailsList) {

        sqlSession = SqlSessionFactoryUtil.openSqlSession();
        RealTimeNewsMapper realTimeNewsMapper = sqlSession.getMapper(RealTimeNewsMapper.class);

        List<RealTimeNewsDetailsPO> newsDetailsPOs;

        for (RealTimeNewsDetailsPO newsDetails : newsDetailsList) {

            //最早的时候有重复数据，这里增加一个逻辑，数据库中已经有的，不再插入数据库
            newsDetailsPOs = realTimeNewsMapper.findAllNewsWhereByNewsId(newsDetails.getNews_id());

            if (CollectionUtils.isEmpty(newsDetailsPOs)) {

                //数据库、内存中唯一标示(长度不限定)、毫秒+随机十位数
                StringBuffer key = new StringBuffer();
                key.append(System.nanoTime()).append(RandomStringUtils.randomNumeric(10));
                newsDetails.setUniqueMark(key.toString());
                newsDetails.setSummary_create_time("");
                newsDetails.setSummary_update_time("");
                newsDetails.setSummary("");
                newsDetails.setContent("");

                int resultNumber = realTimeNewsMapper.insertNewsToRealTimeNewsInfo(newsDetails);
                LOGGER.info("插入条数 : " + resultNumber);
            } else {
                //news_id数据库中已经存在，不再重复插入
                LOGGER.info("news_id : {} , 该条数据 数据库中已存在。", new Object[]{newsDetails.getNews_id()});
            }
        }
    }
}
