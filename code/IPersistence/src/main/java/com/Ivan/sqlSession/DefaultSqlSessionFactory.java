package com.Ivan.sqlSession;

import com.Ivan.pojo.Configuration;

/**
 * @author apple
 * @date 2021/4/3 下午12:22
 * @description
 */
public class DefaultSqlSessionFactory implements  SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
