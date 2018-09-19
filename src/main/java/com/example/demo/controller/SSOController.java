package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.User;
import com.example.demo.jpaRepository.UserRepository;
import com.example.demo.util.StringUtils;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Asuka on 2018/3/7.
 */
@RestController
public class SSOController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/sso")
    @ResponseBody
    public JSONObject sso(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String,String[]> map = request.getParameterMap();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Util.fail("用户名或者密码为空");
        }
        List<User> a = userRepository.findByUsernameAndPassword(username,password);
        if (a.isEmpty()) {
            return Util.fail("用户名或者密码错误");
        }
        Cookie c = new Cookie("sso",a.get(0).getUsername());
        c.setPath("/");
        response.addCookie(c);
        return Util.success();
    }

    @PostMapping(value = "/checkCookie")
    public JSONObject checkCookie(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //根据cookie判断是否登陆
        boolean flag = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie c:cookies) {
                if (c == null) {
                    continue;
                }
                if (c.getName().equals("sso")) {
                    flag = true;
                    break;
                }
            }
        }
        JSONObject jo = new JSONObject();
        if (flag) {
            jo.put("code", "login");
            jo.put("info", "已登陆");
        } else {
            jo.put("code", "noLogin");
            jo.put("info", "未登陆");
        }
        return jo;
    }

}
