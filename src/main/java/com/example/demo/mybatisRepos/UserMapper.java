package com.example.demo.mybatisRepos;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Created by Asuka on 2018/1/20.
 */
@Mapper
public interface UserMapper {

    JSONObject selectUserById(Map<String,Object> param);

    JSONObject selectUserByUsernameAndPassword(Map<String,Object> param);


}
