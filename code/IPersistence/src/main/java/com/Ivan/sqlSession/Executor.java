package com.Ivan.sqlSession;

import com.Ivan.pojo.Configuration;
import com.Ivan.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author apple
 * @date 2021/4/3 下午12:42
 * @description
 */
public interface Executor {

    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException;

    public Integer update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException;

}
