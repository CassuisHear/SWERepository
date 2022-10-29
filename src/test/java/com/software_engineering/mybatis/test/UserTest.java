package com.software_engineering.mybatis.test;

import com.software_engineering.mybatis.mapper.UserMapper;
import com.software_engineering.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class UserTest {

    @Test
    public void testUser() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = factory.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        int result = userMapper.insert(new User(null, "wangwu", "123456", "4321432143214321"));
        System.out.println("result = " + result);
    }
}
