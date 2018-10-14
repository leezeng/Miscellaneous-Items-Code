package com.server.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON工具类
 *
 * @author CYX
 * @create 2018-07-15-11:04
 */
public class JSONUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtil.class);

    /**
     * JSON字符串转换成特定对象，要求对象属性和JSON的key对象
     */
    public static final <T> T toJavaObject(String json, Class<T> clazz) {
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            return JSONObject.toJavaObject(jsonObject, clazz);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * JSON字符串转换成特定对象列表，要求对象属性和JSON的key对应
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> List<T> toJavaArray(String json, Class<T> clazz) {

        List<T> javaList = new ArrayList<T>();
        if (StringUtils.isNotEmpty(json)) {
            try {
                JSONArray jsonArray = JSONObject.parseArray(json);
                JSONObject jsonObject;
                for (int i = 0; i < jsonArray.size(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    javaList.add(JSONObject.toJavaObject(jsonObject, clazz));
                }
            } catch (Exception e) {
                LOGGER.error("json Array 转换异常 : {} , json值 : {}", new Object[]{e, json});
            }
        }
        return javaList;
    }

}
