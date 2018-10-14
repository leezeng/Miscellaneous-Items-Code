package com.server.bean;

import com.server.po.RealTimeNewsDetailsPO;

import java.util.List;

/**
 * 实时新闻集合信息
 *
 * @author CYX
 * @create 2018-07-15-10:08
 */
public class RealTimeNewsCollection {

    /**
     * 数据库、内存中唯一标示(长度不限定)
     */
    private String uniqueMark;
    /**
     * data中news结构返回的新闻数量。
     */
    private Integer count;
    /**
     * 实时新闻请求API提供分页功能，first_id表示本次返回第一条新闻的ID。
     */
    private String first_id;
    /**
     * 实时新闻请求API提供分页功能，last_id表示本次返回最后一条新闻的ID。
     */
    private String last_id;
    /**
     * 集合中的新闻详情信息
     */
    //private List<RealTimeNewsDetails> news;
    private List<RealTimeNewsDetailsPO> news;

    public List<RealTimeNewsDetailsPO> getNews() {
        return news;
    }

    public void setNews(List<RealTimeNewsDetailsPO> news) {
        this.news = news;
    }

    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getFirst_id() {
        return first_id;
    }

    public void setFirst_id(String first_id) {
        this.first_id = first_id;
    }

    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }

    @Override
    public String toString() {
        return "RealTimeNewsCollection{" +
                "uniqueMark='" + uniqueMark + '\'' +
                ", count=" + count +
                ", first_id='" + first_id + '\'' +
                ", last_id='" + last_id + '\'' +
                ", news=" + news +
                '}';
    }
}
