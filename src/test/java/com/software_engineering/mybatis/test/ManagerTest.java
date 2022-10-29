package com.software_engineering.mybatis.test;

import com.software_engineering.mybatis.mapper.ManagerMapper;
import com.software_engineering.pojo.Manager;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ManagerTest {

    @Test
    public void testManager() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = factory.openSession(true);
        ManagerMapper managerMapper = sqlSession.getMapper(ManagerMapper.class);

        int result = managerMapper.insert(new Manager(-1, "admin", "admin"));
        System.out.println("result = " + result);
    }
}
