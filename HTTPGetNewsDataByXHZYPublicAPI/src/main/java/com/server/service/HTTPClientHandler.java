package com.server.service;

import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调用HTTPClient
 *
 * @author CYX
 * @create 2018-07-22-13:42
 */
public class HTTPClientHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPClientHandler.class);

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    /**
     * HTTPClient-get方法-实时新闻获取
     */
    public CloseableHttpResponse getHTTPClientRealTimeNewsAcquisition(URIBuilder builder) throws Exception {

        HttpGet httpGet = new HttpGet(builder.build());

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(6000).setConnectTimeout(6000)
                .setConnectionRequestTimeout(6000).build();

        httpGet.setConfig(requestConfig);

        LOGGER.info("url : " + httpGet.getURI().toString());

        CloseableHttpResponse response = httpClient.execute(httpGet);

        return response;
    }


    /**
     * HTTPClient-get方法-获取新闻详情
     *
     * @throws Exception
     */
    public CloseableHttpResponse getHTTPClientNewsDetails(String url) throws Exception {

        // 以get方式请求该URL
        HttpGet httpget = new HttpGet(url);

        httpget.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");

        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(10000).setConnectTimeout(10000).build();
        httpget.setConfig(requestConfig);

        // 得到response对象
        CloseableHttpResponse response = httpClient.execute(httpget);

        return response;
    }

}
