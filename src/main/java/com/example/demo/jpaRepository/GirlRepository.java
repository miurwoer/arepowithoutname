package com.example.demo.jpaRepository;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.domain.Girl;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * girl,Repository
 * @author Asuka
 *
 */
public interface GirlRepository extends JpaRepository<Girl,Integer>{

    //通过年龄查询
    public JSONArray findByAge(int age);

    //通过罩杯查询
    public JSONArray findByCupSize(String cupSize);
}
