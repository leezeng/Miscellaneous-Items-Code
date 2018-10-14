
#数据库
CREATE DATABASE news;

#实时新闻信息表
CREATE TABLE `real_time_news_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据库id',
  `uniqueMark` varchar(255) NOT NULL DEFAULT '' COMMENT '业务id',
  `news_id` varchar(255) NOT NULL DEFAULT '' COMMENT '新闻id',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '新闻标题',
  `source` varchar(255) NOT NULL DEFAULT '' COMMENT '新闻来源',
  `gmt_publish` varchar(255) NOT NULL DEFAULT '' COMMENT '发布时间',
  `hot_index` varchar(255) NOT NULL DEFAULT '' COMMENT '热门指数',
  `category` varchar(255) NOT NULL DEFAULT '' COMMENT '新闻分类',
  `thumbnail_img` varchar(255) NOT NULL DEFAULT '' COMMENT '新闻封面缩略图地址',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '分发平台提供的新闻链接',
  `summary_create_time` varchar(255) NOT NULL DEFAULT '' COMMENT '摘要创建时间',
  `summary_update_time` varchar(255) NOT NULL DEFAULT '' COMMENT '摘要更新时间',
  `summary` varchar(255) NOT NULL DEFAULT '' COMMENT '新闻内容摘要',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '新闻文本内容',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;