package com.Ivan.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;

/**
 * @author apple
 * @date 2021/4/3 下午12:28
 * @description
 */
public interface SqlSession {

    // 查询所有
    public <E> List<E> selectList(String statementId,Object... params) throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    // 根据条件查询单个
    public <T> T selectOne(String statementId,Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException;

    public Integer update(String statementId,Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException;

    // 为dao接口实现代理实现类
    public <T> T getMapper(Class<?> mapperClass);
}
