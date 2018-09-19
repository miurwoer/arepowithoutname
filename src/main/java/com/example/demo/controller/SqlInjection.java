package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.jdbc.Dao;
import com.example.demo.mybatisRepos.UserMapper;
import com.example.demo.util.StringUtils;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * sql注入演示
 * Created by Asuka on 2018/3/13.
 */
@RestController
public class SqlInjection {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Dao dao;


    @RequestMapping(value = "/login")
    @ResponseBody
    public JSONObject login(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(username)) {
            return Util.fail("用户名或者密码为空");
        }

        JSONObject param = new JSONObject();
        param.put("username",username);
        param.put("password",password);
        JSONObject jo = userMapper.selectUserByUsernameAndPassword(param);
        return jo;
    }

    @RequestMapping(value = "/search")
    @ResponseBody
    public JSONObject search(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            return Util.fail("用户名或者密码为空");
        }

        JSONObject param = new JSONObject();
        param.put("user_id",id);
        JSONObject jo = userMapper.selectUserById(param);
        return jo;
    }

    @RequestMapping(value = "/searchJdbc")
    @ResponseBody
    public JSONObject searchJdbc(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String user_id = request.getParameter("id");
        if (StringUtils.isEmpty(user_id)) {
            return Util.fail("用户名或者密码为空haha");
        }
        Pattern p = Pattern.compile("^\\d+$");
        int id = 0;
        if (p.matcher(user_id).matches()) {
            id = Integer.valueOf(user_id);
        }

        JSONObject param = new JSONObject();
        param.put("user_id",id);
        JSONObject jo = dao.selectUserById(param);
        return jo;
    }
}
