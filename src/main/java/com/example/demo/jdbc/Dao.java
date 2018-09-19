package com.example.demo.jdbc;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

/**
 * Created by Asuka on 2018/3/13.
 */
@Component
public class Dao {

    public JSONObject selectUserById(Map<String,Object> param) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useUnicode=true" +
                "&createDatabaseIfNotExist=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
        String username = "root";
        String password = "123";
        Connection con = DriverManager.getConnection(url,username,password);
        String sql = "select * from User where user_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setObject(1,param.get("user_id"));

        sql = sql.replace("?","'"+param.get("user_id")+"'");
        System.out.println(sql);

        ResultSet rs = ps.executeQuery();
        JSONObject jo = null;
        while(rs.next()){
            int id = rs.getInt("user_id");
            String name = rs.getString(2);
            String pass = rs.getString(3);
            jo = new JSONObject();
            jo.put("user_id",id);
            jo.put("username",name);
            jo.put("password",pass);
        }
        rs.close();
        ps.close();
        con.close();

        return jo;
    }
}
