package com.software_engineering.springMVC.controller;

import com.software_engineering.mybatis.mapper.UserMapper;
import com.software_engineering.pojo.User;
import com.software_engineering.pojo.UserExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class UserController {

    static {
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = factory.openSession(true);
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    private final static UserMapper userMapper;

    @PostMapping("/registerUser")
    public ModelAndView registerUser(String username, String password, ModelAndView mav) {
        userMapper.insert(new User(null, username, password, null));

        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);

        User user = users.get(0);
        if (user.getAccount() == null) {
            mav.setViewName("userNoAccount");
        } else {
            mav.addObject("userInfo", user);
            mav.setViewName("user");
        }

        return mav;
    }
}
