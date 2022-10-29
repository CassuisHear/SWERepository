package com.software_engineering.springMVC.controller;

import com.software_engineering.mybatis.mapper.ManagerMapper;
import com.software_engineering.mybatis.mapper.UserMapper;
import com.software_engineering.pojo.Manager;
import com.software_engineering.pojo.ManagerExample;
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
public class PageController {

    static {
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = factory.openSession(true);
        managerMapper = sqlSession.getMapper(ManagerMapper.class);
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    private final static ManagerMapper managerMapper;
    private final static UserMapper userMapper;

    @PostMapping("/login")
    public ModelAndView login(String username, String password, ModelAndView mav) {

        UserExample userExample = new UserExample();
        ManagerExample managerExample = new ManagerExample();

        userExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        managerExample.createCriteria().andManagerNameEqualTo(username).andPasswordEqualTo(password);

        List<User> users = userMapper.selectByExample(userExample);
        List<Manager> managers = managerMapper.selectByExample(managerExample);

        if (!users.isEmpty()) {
            User user = users.get(0);
            if(user.getAccount() == null){
                mav.setViewName("userNoAccount");
            }else{
                mav.addObject("userInfo", user);
                mav.setViewName("user");
            }
        } else if (!managers.isEmpty()) {
            mav.addObject("managerInfo", managers.get(0));
            mav.setViewName("manager");
        } else {
            mav.addObject("message", "用户名或密码错误");
            mav.setViewName("index");
        }
        return mav;
    }
}
