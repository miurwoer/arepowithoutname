package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.aspect.HttpAspect;
import com.example.demo.domain.User;
import com.example.demo.jpaRepository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Asuka on 2018/7/9.
 */
@RestController
public class OilController {

    private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/oilLogin")
    public JSONObject login(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String pswd = request.getParameter("pswd");

        logger.info("name:" + name + ",pswd:" + pswd);

        JSONObject result = new JSONObject();
        List<User> userList = userRepository.findByUsernameAndPassword(name, pswd);
        if (CollectionUtils.isEmpty(userList)) {
            result.put("code","-1");
            result.put("info","用户名或者密码错误");
            return result;
        }

        result.put("code","0");
        result.put("info","ok");
        return result;
    }

}
