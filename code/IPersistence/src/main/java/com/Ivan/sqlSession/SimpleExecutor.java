package com.Ivan.sqlSession;

import com.Ivan.pojo.Configuration;
import com.Ivan.pojo.MappedStatement;
import com.Ivan.utils.GenericTokenParser;
import com.Ivan.utils.ParameterMapping;
import com.Ivan.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author apple
 * @date 2021/4/3 下午12:43
 * @description
 */
public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException {

        // 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        // 获取sql,转换语句
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        // 获取预处理对象：prepareStatement()
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 设置参数
            // 获取参数全路径
        String parameterType = mappedStatement.getParameterType();
        Class<?> paramtertypeClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for(int i = 0; i < parameterMappingList.size();i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            // 反射
            Field declaredField = paramtertypeClass.getDeclaredField(content);
            // 暴力访问解除访问权限
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i+1,o);

        }
        // 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);

        ArrayList<Object> objects = new ArrayList<>();

        // 封装返回结果集
        while(resultSet.next()) {
            Object o = resultTypeClass.newInstance();
            // 元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for(int i = 1; i <= metaData.getColumnCount();i++) {
                // 获取字段名
                String columnName = metaData.getColumnName(i);
                // 获取字段值
                Object value = resultSet.getObject(columnName);
                // 使用反射或者内省,根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            objects.add(o);
        }

        return (List<E>) objects;
    }

    @Override
    public Integer update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {

        // 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        // 获取sql,转换语句
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        // 获取预处理对象：prepareStatement()
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 设置参数
        // 获取参数全路径
        String parameterType = mappedStatement.getParameterType();
        Class<?> paramtertypeClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for(int i = 0; i < parameterMappingList.size();i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            // 反射
            Field declaredField = paramtertypeClass.getDeclaredField(content);
            // 暴力访问解除访问权限
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i+1,o);

        }
        // 执行sql
        int res = preparedStatement.executeUpdate();

        return res;
    }


    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if(parameterType != null) {
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }
        return  null;
    }

    /**
     * 完成对#{}的解析，使用？代替#{}，解析出#{}里的值进行存储
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {

        // 标记处理类，配合标记解析器完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);

        // 解析的sql
        String parseSql = genericTokenParser.parse(sql);

        // #{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);

        return boundSql;

    }
}
