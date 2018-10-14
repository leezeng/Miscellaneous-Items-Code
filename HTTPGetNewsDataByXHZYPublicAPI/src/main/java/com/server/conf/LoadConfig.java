package com.server.conf;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 加载配置文件
 *
 * @author CYX
 * @create 2018-07-03-7:08
 */
public class LoadConfig {

    private static class LoadConfigHolder {
        private static final LoadConfig INSTANCE = new LoadConfig();
    }

    public static final LoadConfig getInstance() {
        return LoadConfigHolder.INSTANCE;
    }

    private String accessKey;

    private String secretKey;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    /**
     * 加载config.properties配置文件
     */
    public void loadConfigProperties() throws Exception {

        Properties properties = new Properties();
        properties.load(new FileInputStream(new File("./conf/config.properties")));

        accessKey = properties.getProperty("accessKey");
        secretKey = properties.getProperty("secretKey");
    }

    /**
     * 手动加载logback日志配置文件
     *
     * @param logBackConfigFileStr logBack配置文件路径
     * @throws IOException
     * @throws JoranException
     */
    public void loadLogBack(String logBackConfigFileStr) throws IOException, JoranException {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        File externalConfigFile = new File(logBackConfigFileStr);
        if (!externalConfigFile.exists()) {
            throw new IOException("Logback External Config File Parameter does not reference a file that exists");
        } else {
            if (!externalConfigFile.isFile()) {
                throw new IOException("Logback External Config File Parameter exists, but does not reference a file");
            } else {
                if (!externalConfigFile.canRead()) {
                    throw new IOException("Logback External Config File exists and is a file, but cannot be read.");
                } else {
                    JoranConfigurator configurator = new JoranConfigurator();
                    configurator.setContext(lc);
                    lc.reset();
                    configurator.doConfigure(logBackConfigFileStr);
                    StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
                }
            }
        }
    }

}
