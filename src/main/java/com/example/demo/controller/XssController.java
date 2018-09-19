package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Asuka on 2018/3/5.
 */
@RestController
public class XssController {

    @RequestMapping(value = "/xss")
    @ResponseBody
    public JSONObject responseXss(HttpServletRequest request, HttpServletResponse response){
        String xss = request.getParameter("xss");
        xss = Util.encode(xss);
        JSONObject jo = new JSONObject();
        jo.put("code","1");
        jo.put("result",xss);
        return jo;
    }


}
