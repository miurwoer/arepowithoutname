package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.XiaoLinProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Asuka on 2018/1/14.
 */
@RestController
public class HelloController {

    @Autowired
    private XiaoLinProperties xiaoLinProperties;

    @Value("${database}")
    private String database;

    @RequestMapping(value= "/hello",method = RequestMethod.GET)
    public String say(){
        return database;
    }

    @RequestMapping(value= "/xiaolin/{id}",method = RequestMethod.GET)
    public String getxl(@PathVariable(value = "id") Integer username){
        return "name is "+xiaoLinProperties.getName()+" age is "+xiaoLinProperties.getAge();
    }

    @GetMapping("/wenxu")
    public String getW(@RequestParam(value="age",required = false,defaultValue = "0") Integer username){
        return "name is "+xiaoLinProperties.getName()+" age is "+xiaoLinProperties.getAge();
    }

    @GetMapping(value = "/getPath")
    public JSONObject getPath(HttpServletRequest request, HttpServletResponse response){
        String realPath = request.getServletContext().getRealPath("abcd.txt");

        System.out.println("realPath\t" + realPath);

        String classpath = "/" + (String.valueOf(Thread.currentThread().getContextClassLoader()
                .getResource(""))+"../../").replaceAll("file:/", "").replaceAll("%20", " ").trim();

        System.out.println("classpath\t" + classpath);

        try {
            ClassPathResource classPathResource = new ClassPathResource("/mapper/GirlMapper.xml");
            InputStream in = null;
            in = classPathResource .getInputStream();

            int length = 0;
            byte [] bs = new byte[1024];
            StringBuffer sb = new StringBuffer();
            while ((length = in.read(bs)) != -1) {
                sb.append(new String(bs,0,length,"utf-8"));
            }
            System.out.println("classPathResource\t" + sb.toString());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
