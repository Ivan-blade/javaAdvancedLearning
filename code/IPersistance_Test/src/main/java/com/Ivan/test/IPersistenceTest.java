package com.Ivan.test;

import com.Ivan.dao.UserDao;
import com.Ivan.io.Resources;
import com.Ivan.pojo.User;
import com.Ivan.sqlSession.SqlSession;
import com.Ivan.sqlSession.SqlSessionFactory;
import com.Ivan.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author apple
 * @date 2021/4/2 上午10:40
 * @description
 */
public class IPersistenceTest {

    @Test
    public void testOld() throws PropertyVetoException, DocumentException, IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();


        // 调用
        User user = new User();
        user.setId(1);
        user.setUsername("ivan");
        // User user1 = sqlSession.selectOne("user.selectOne", user);

        // System.out.println(user1);

        List<User> objects = sqlSession.selectList("user.selectList");
        for (User object : objects) {
            System.out.println(object);
        }

    }

    @Test
    public void testMapper() throws PropertyVetoException, DocumentException, IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();


        // 调用
        User user = new User();
        user.setId(1);
        user.setUsername("ivan");

        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> all = mapper.findAll();
        User user2 = mapper.findByCondition(user);
        System.out.println(user2);
        System.out.println(all);

    }

    @Test
    public void testInsert() throws PropertyVetoException, DocumentException, IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();


        // 调用
        UserDao mapper = sqlSession.getMapper(UserDao.class);

        // 增
        User user = new User();
        user.setId(3);
        user.setUsername("saber");
        Integer insert = mapper.insert(user);
        System.out.println(mapper.findAll());

    }

    @Test
    public void testUpdate() throws PropertyVetoException, DocumentException, IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();


        // 调用
        UserDao mapper = sqlSession.getMapper(UserDao.class);

        // 增
        User user = new User();

        // 改
        user.setId(3);
        user.setUsername("zookeeper");
        Integer update = mapper.update(user);
        System.out.println(mapper.findAll());

    }

    @Test
    public void testDelete() throws PropertyVetoException, DocumentException, IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();


        // 调用
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        // 增
        User user = new User();
        user.setId(3);

        System.out.println(mapper.delete(user));

        System.out.println(mapper.findAll());

    }

    @Test
    public void testAll() throws PropertyVetoException, DocumentException, IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();


        // 调用
        UserDao mapper = sqlSession.getMapper(UserDao.class);

        // 增
        User user = new User();
        user.setId(3);
        user.setUsername("saber");
        Integer insert = mapper.insert(user);
        System.out.println(mapper.findAll());

        // 改
        user.setId(3);
        user.setUsername("zookeeper");
        Integer update = mapper.update(user);
        System.out.println(mapper.findAll());

        // 查
        System.out.println(mapper.findByCondition(user));

        // 删
        Integer delete = mapper.delete(user);

        System.out.println(mapper.findAll());

    }
}
