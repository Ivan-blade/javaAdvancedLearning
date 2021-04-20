package com.Ivan.dao;

import com.Ivan.io.Resources;
import com.Ivan.pojo.User;
import com.Ivan.sqlSession.SqlSession;
import com.Ivan.sqlSession.SqlSessionFactory;
import com.Ivan.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author apple
 * @date 2021/4/3 下午2:29
 * @description
 */

// 不使用动态代理的处理方式
public class UserDaoImpl /*implements UserDao*/{

    /*@Override
    public List<User> findAll() throws PropertyVetoException, DocumentException, IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();


        List<User> objects = sqlSession.selectList("user.selectList");
        for (User object : objects) {
            System.out.println(object);
        }
        return objects;
    }

    @Override
    public User findByCondition(User user) throws PropertyVetoException, DocumentException, IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user1 = sqlSession.selectOne("user.selectOne", user);

        System.out.println(user1);
        return user1;

    }*/
}
