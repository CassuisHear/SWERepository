package com.software_engineering.springMVC.controller;

import com.software_engineering.mybatis.mapper.UserMapper;
import com.software_engineering.pojo.User;
import com.software_engineering.pojo.UserExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class ManagerController {

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

    @GetMapping("/addUser")
    public ModelAndView addUser(ModelAndView mav) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIsNull();
        List<User> users = userMapper.selectByExample(userExample);

        if (users.isEmpty()) {
            mav.setViewName("noUsers");
        } else {
            mav.addObject("users", users);
            mav.setViewName("user_add");
        }
        return mav;
    }

    @GetMapping("/user/{id}")
    public ModelAndView getUserById(@PathVariable("id") Integer id, ModelAndView mav) {
        User user = userMapper.selectByPrimaryKey(id);
        mav.addObject("user", user);
        mav.setViewName("user_update");
        return mav;
    }

    @PutMapping("/user")
    public String updateUser(User user) {
        userMapper.updateByPrimaryKey(user);
        return "redirect:/addUser";
    }

    @GetMapping("/alterUser")
    public ModelAndView alterUser(ModelAndView mav) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIsNotNull();
        List<User> users = userMapper.selectByExample(userExample);

        if (users.isEmpty()) {
            mav.setViewName("noUsers");
        } else {
            mav.addObject("users", users);
            mav.setViewName("user_alter");
        }
        return mav;
    }

    @PutMapping("/userAlter")
    public String userAlter(User user) {
        userMapper.updateByPrimaryKey(user);
        return "redirect:/alterUser";
    }

    @GetMapping("/deleteUser")
    public ModelAndView deleteUser(ModelAndView mav) {

        List<User> users = userMapper.selectByExample(null);

        if (users.isEmpty()) {
            mav.setViewName("noUsers");
        } else {
            mav.addObject("users", users);
            mav.setViewName("user_delete");
        }
        return mav;
    }

    @RequestMapping("/userDelete/{id}")
    public String deleteUserById(@PathVariable("id") Integer id) {
        userMapper.deleteByPrimaryKey(id);
        return "redirect:/deleteUser";
    }
}
