package com.server.bean;

import java.util.List;

/**
 * 实时新闻详情
 *
 * @author CYX
 * @create 2018-07-15-10:11
 */
public class RealTimeNewsDetails {

    /**
     * 数据库、内存中唯一标示(长度不限定)
     */
    private String uniqueMark;
    /**
     * 新闻ID，平台内唯一。
     */
    private String news_id;
    /**
     * 新闻标题。
     */
    private String title;
    /**
     * 新闻来源，包括新华社及全国其他几十家媒体机构。
     */
    private String source;
    /**
     * 发布时间，详细到分钟级别。
     */
    private Integer gmt_publish;
    /**
     * 热门指数，表明该新闻热度。最小值为0。
     */
    private Integer hot_index;
    /**
     * 新闻分类，新闻频道详细信息科参考
     * <br>
     * https://fenfa.shuwen.com/docs/api_category?spm=fenfa.0.0.1.0o6giN
     */
    private List<String> category;
    /**
     * 可选项。新闻封面缩略图地址，可以有多个缩略图。
     */
    private List<String> thumbnail_img;
    /**
     * 分发平台提供的新闻链接。
     */
    private String url;
    /**
     * 摘要创建时间。不提供摘要内容时字段值为null。
     */
    private String summary_create_time;
    /**
     * 摘要更新时间。 不提供摘要内容时字段值为null。
     */
    private String summaryu_update_time;
    /**
     * 新闻内容摘要，250字以内。。默认不提供，如您需要，可通过 business@shuwen.com 联系我们。
     */
    private String summary;
    /**
     * 新闻文本内容。默认不提供，如您需要，可通过 business@shuwen.com 联系我们。
     */
    private String content;

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getGmt_publish() {
        return gmt_publish;
    }

    public void setGmt_publish(Integer gmt_publish) {
        this.gmt_publish = gmt_publish;
    }

    public Integer getHot_index() {
        return hot_index;
    }

    public void setHot_index(Integer hot_index) {
        this.hot_index = hot_index;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getThumbnail_img() {
        return thumbnail_img;
    }

    public void setThumbnail_img(List<String> thumbnail_img) {
        this.thumbnail_img = thumbnail_img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary_create_time() {
        return summary_create_time;
    }

    public void setSummary_create_time(String summary_create_time) {
        this.summary_create_time = summary_create_time;
    }

    public String getSummaryu_update_time() {
        return summaryu_update_time;
    }

    public void setSummaryu_update_time(String summaryu_update_time) {
        this.summaryu_update_time = summaryu_update_time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }

    @Override
    public String toString() {
        return "RealTimeNewsDetails{" +
                "uniqueMark='" + uniqueMark + '\'' +
                ", news_id='" + news_id + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", gmt_publish=" + gmt_publish +
                ", hot_index=" + hot_index +
                ", category=" + category +
                ", thumbnail_img=" + thumbnail_img +
                ", url='" + url + '\'' +
                ", summary_create_time='" + summary_create_time + '\'' +
                ", summaryu_update_time='" + summaryu_update_time + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
