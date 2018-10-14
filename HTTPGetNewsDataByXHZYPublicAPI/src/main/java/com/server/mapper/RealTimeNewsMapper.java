package com.server.mapper;

import com.server.po.RealTimeNewsDetailsPO;

import java.util.List;

/**
 * 实时新闻-MyBatis-Mapper接口
 *
 * @author CYX
 * @create 2018-07-15-13:26
 */
public interface RealTimeNewsMapper {

    /**
     * 插入一条实时新闻
     *
     * @param newsDetails 实时新闻
     * @return
     */
    int insertNewsToRealTimeNewsInfo(RealTimeNewsDetailsPO newsDetails);

    /**
     * 根据Content字段查找新闻
     *
     * @param contentStr
     * @return
     */
    List<RealTimeNewsDetailsPO> findAllNewsWhereByContent(String contentStr);

    /**
     * 更新新闻信息
     *
     * @param newsDetails
     * @return
     */
    int updateNewsToRealTimeNewsInfo(RealTimeNewsDetailsPO newsDetails);

    /**
     * 根据news_id查找新闻信息
     *
     * @param news_id
     * @return
     */
    List<RealTimeNewsDetailsPO> findAllNewsWhereByNewsId(String news_id);

}
