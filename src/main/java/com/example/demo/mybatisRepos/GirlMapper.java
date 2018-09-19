package com.example.demo.mybatisRepos;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Asuka on 2018/1/20.
 */
@Mapper
public interface GirlMapper {

    /*@Select("select * from girl where id = #{id}")
    JSONObject select(Map<String,Object> girls);

    @Select("select * from girl")
    @Results({
            @Result(properties = "cupSize",column = "cup_size")
    })
    List<Girl> findAll();*/

    List<Map<String,Object>> selectAll();
}
