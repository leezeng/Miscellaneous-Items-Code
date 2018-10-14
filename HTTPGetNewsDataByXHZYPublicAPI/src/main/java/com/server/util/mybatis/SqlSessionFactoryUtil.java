package com.server.util.mybatis;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlSessionFactoryUtil.class);

    /**
     * SqlSessionFactory对象
     */
    private static SqlSessionFactory sqlSessionFactory = null;

    /**
     * 类线程锁
     */
    private static final Class CLASS_LOCK = SqlSessionFactoryUtil.class;

    public SqlSessionFactoryUtil() {
    }

    /**
     * 构建SqlSessionFactory
     *
     * @return
     */
    private static SqlSessionFactory initSqlSessionFactory() {
        InputStream inputStream = null;
        try {
            String resource = "./conf/mybatis/mybatis_Config.xml";
            inputStream = IOUtils.toInputStream(FileUtils.readFileToString(new File(resource), "UTF-8"), "UTF-8");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        synchronized (CLASS_LOCK) {
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }
        }

        return sqlSessionFactory;
    }

    /**
     * 打开SqlSession
     *
     * @return
     */
    public static SqlSession openSqlSession() {
        if (sqlSessionFactory == null) {
            initSqlSessionFactory();
        }
        return sqlSessionFactory.openSession();
    }

}
