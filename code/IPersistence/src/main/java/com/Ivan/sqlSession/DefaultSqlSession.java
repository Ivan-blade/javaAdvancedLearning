package com.Ivan.sqlSession;

import com.Ivan.pojo.Configuration;
import com.Ivan.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author apple
 * @date 2021/4/3 下午12:29
 * @description
 */
public class DefaultSqlSession implements SqlSession {


    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {

        // 完成对simpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> query = simpleExecutor.query(configuration, mappedStatement, params);

        return (List<E>) query;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException {

        List<Object> objects = selectList(statementId, params);
        if(objects.size() == 1) return (T) objects.get(0);
        else throw new RuntimeException("invaild output!");
    }

    public Integer update(String statementId,Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        Integer res = simpleExecutor.update(configuration,mappedStatement,params);

        return res;
    }

    // 为dao接口实现代理实现类
    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用jdk动态代理为dao接口实现代理对象并返回
        Object o = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层还是调用jdbc，根据不同情况调用不同方法

                // 准备参数1：statementId：namespace.id
                // 因为目前无法直接获取配置文件的值，于是我们引入一定的规范来获取默认值，即namespace和dao的全限定名一致，id和类下的方法名一致
                String name = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className+"."+name;

                // 准备参数2：params：args
                // 获取被调用方法返回值类型
                Type genericReturnType = method.getGenericReturnType();
                // 先判断是否为增删改操作，这三种操作都只需要调用底层的update方法即可
                if("java.lang.Integer".equals(genericReturnType.getTypeName())) {
                    return update(statementId,args);
                }
                // 是否进行了泛型类型参数化，如果泛型化返回结果集就是集合否则是单个对象
                if(genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }
                return selectOne(statementId,args);
            }
        });
        return (T) o;
    }
}
