package com.server.spring.spring03;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author CYX
 * @create 2018-04-16-22:40
 */
public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Map<String, String> ctxPropMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);

        ctxPropMap = new HashMap<String, String>();

        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String valueStr = String.valueOf(props.getProperty(keyStr));
            ctxPropMap.put(keyStr, valueStr);
        }

    }

    public static String getCtxPro(String name) {
        return ctxPropMap.get(name);
    }

    public static Map<String, String> getCtxPropMap() {
        return ctxPropMap;
    }

}
