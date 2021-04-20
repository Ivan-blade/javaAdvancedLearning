package com.Ivan.dao;

import com.Ivan.pojo.User;
import org.dom4j.DocumentException;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author apple
 * @date 2021/4/3 下午2:27
 * @description
 */
public interface UserDao {

    // 查询所有用户
    public List<User> findAll() throws PropertyVetoException, DocumentException, IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;


    // 根据条件进行查询
    public User findByCondition(User user) throws PropertyVetoException, DocumentException, IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    // 插入
    public Integer insert(User user);

    // 更新
    public Integer update(User user);

    // 删除
    public Integer delete(User user);
}
