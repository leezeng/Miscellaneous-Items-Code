package com.server.common;

import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author CYX
 * @create 2018-05-22-22:04
 */
@Service
public class CommonUtils {

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    /**
     * 根据模板和参数输出HTML字符串
     *
     * @param module       参数map
     * @param templatePath 模板路径
     * @return
     */
    public String getOutputHtmlStr(Map<String, Object> module, String templatePath) {
        StringWriter stringWriter = new StringWriter();
        try {
            Template template = freeMarkerConfig.getConfiguration().getTemplate(templatePath);
            template.process(module, stringWriter);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return stringWriter.toString();
    }

    /**
     * 向页面输出结果
     *
     * @param response
     * @param jsonStr
     */
    public void flushResultToPage(HttpServletResponse response, String jsonStr) {
        try {
            response.setContentType("text/html");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            String json = jsonStr;
            PrintWriter writer = response.getWriter();
            writer.print(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
