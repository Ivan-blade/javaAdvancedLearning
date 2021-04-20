package com.Ivan.sqlSession;

import com.Ivan.config.XMLConfigBuilder;
import com.Ivan.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author apple
 * @date 2021/4/2 上午11:23
 * @description
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException {

        // 使用dom4j解析配置文件，将内容封装到configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);

        // 创建sqlsessionfactory,工厂类，主要职责就是生成SqlSession
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;
    }
}
