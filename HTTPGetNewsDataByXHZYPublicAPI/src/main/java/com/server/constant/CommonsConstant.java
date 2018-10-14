package com.server.constant;

/**
 * 公共常量
 *
 * @author CYX
 * @create 2018-07-15-10:26
 */
public class CommonsConstant {

    /**
     * 颁发给用户的身份识别表示
     * <br>
     * 每个应用的AccessKey信息
     */
    public static final String ACCESS_KEY = "access_key";

    /**
     * 请求的时间戳。GMT当前时间戳，13位数字，精确到毫秒。与服务器时间偏差5分钟内有效。
     */
    public static final String TIMESTAMP = "timestamp";

    /**
     * 签名结果串
     * <br>
     * https://fenfa.shuwen.com/docs/api_signature?spm=fenfa.0.0.1.0o6giN
     * <br>
     * 按照Secret Key、timestamp、Access Key 的顺序，将以上三个参数的值顺序链接，并取MD5值
     * 作为signature的值
     */
    public static final String SIGNATURE = "signature";

    /**
     * 获取某一频道下的历史新闻
     */
    public static final String CATEGORY = "category";

    /**
     * 地域过滤条件。若设置，仅返回相关地域内新闻结果。若未设置，则返回全量结果
     * <br>
     * https://fenfa.shuwen.com/docs/region?spm=fenfa.0.0.1.0o6giN
     */
    public static final String REGION = "region";

    /**
     * 上一次API调用的第一个ID。本次返回仅会给出比此ID更新的新闻。若不设置，从第一条数据开始获取。当同时指定first_id及last_id时会抛出异常。
     */
    public static final String FIRST_ID = "first_id";

    /**
     * 上一次API调用的最后一个ID。本次返回仅会给出此ID后面的新闻。若不设置，从第一条数据开始获取。
     */
    public static final String LAST_ID = "last_id";

    /**
     * 单次调用返回的新闻数量。取值 0-20，默认15。
     */
    public static final String SIZE = "size";

    /**
     * 正则表达式-去除HTML(String)中的标签
     */
    public static final String HTML_REGULAR_EXPRESSION = "\\<.*?>";
}
