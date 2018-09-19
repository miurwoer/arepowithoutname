package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.aspect.HttpAspect;
import com.example.demo.domain.Girl;
import com.example.demo.jpaRepository.GirlRepository;
import com.example.demo.mybatisRepos.GirlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Asuka on 2018/1/14.
 */
@RestController
public class GirlController {

    private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Autowired
    private GirlRepository girlRepository;

    @Autowired
    private GirlMapper mapper;

    /**
     * 查询女生列表
    */
    @GetMapping(value="/getAllGirl")
    public JSONObject getAllGirl(){
        logger.info("方法名称：getAllGirl");
        List<Girl> girlList = girlRepository.findAll();
        JSONArray girls = (JSONArray)JSONArray.toJSON(girlList);
        JSONObject jo = new JSONObject();
        jo.put("code","1");
        jo.put("info",girls);
        return jo;
    }

    /**
     * 添加一个女生
     * */
    @GetMapping(value = "/addOnegirl")
    public JSONObject addOneGirl(@Valid Girl girl, BindingResult bindingResult){
        JSONObject jo = new JSONObject();
        if (bindingResult.hasErrors()){
            jo.put("code","-1");
            jo.put("result",bindingResult.getFieldError().getDefaultMessage());
            return jo;
        }
        Girl girl1 = girlRepository.save(girl);
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(girl1);
        JSONArray array = new JSONArray();
        array.add(jsonObject);
        jo.put("code","1");
        jo.put("result",array);
        return jo;
    }

    /**
     * 根据id查询一个女生
     * */
    @GetMapping(value = "/selectGirlById/{id}")
    public JSONObject selectGirlById(@PathVariable(value = "id") Integer id){
        JSONObject array = (JSONObject)JSONObject.toJSON(girlRepository.findById(id));
        return array;
    }

    /**
     * 根据id更新一个女生的信息
     * */
    @GetMapping(value = "/updateOneGirl/{id}")
    public Girl updateOneGirl(@PathVariable(value = "id",required = false) Integer id,
                                    @RequestParam(value = "age",required = false) Integer age,
                                    @RequestParam(value = "cupSize",required = false) String cupSize){
        Girl girl = new Girl();
        girl.setId(id);
        girl.setAge(age);
        girl.setCupSize(cupSize);
        Girl gril = girlRepository.save(girl);
        return girl;
    }

    /**
     * 根据id删除一个女生
     * */
    @GetMapping(value = "/deleteOneGirl/{id}")
    public void updateOneGirl(@PathVariable(value = "id") Integer id){
        Girl g = new Girl();
        g.setId(id);
        girlRepository.delete(g);
    }

    /**
     * 根据age查询女生列表
     * */
    @GetMapping(value = "/findByAge/{age}")
    public JSONArray findByAge(@PathVariable(value = "age") Integer age){
        return girlRepository.findByAge(age);
    }

    /**
     * 根据cupSize查询女生列表
     * */
    @GetMapping(value = "/findByCupSize/{cupSize}")
    public JSONArray findByCupSize(@PathVariable(value = "cupSize") String cupSize){
        return girlRepository.findByCupSize(cupSize);
    }

    /**
     * 插入两个女生
     * */
    @GetMapping(value = "/addTwoGirls")
    @Transactional
    public List<Girl> addTwoGirls(){
        Girl girl1 = new Girl();
        girl1.setId(1);
        girl1.setAge(23);
        girl1.setCupSize("C");
        Girl girl2 = new Girl();
        girl2.setId(2);
        girl2.setAge(23);
        girl2.setCupSize("Cdfs");
        girl1 = girlRepository.save(girl1);
        girl2 = girlRepository.save(girl2);
        List<Girl> girls = new ArrayList<Girl>();
        girls.add(girl1);
        girls.add(girl2);
        return girls;
    }

    /**
     * mybatis根据id查询
    * */
/*    @GetMapping(value = "/select/{id}")
    public JSONObject select(@PathVariable(value = "id") Integer id){
        JSONObject jo = new JSONObject();
        jo.put("id",id);
        JSONObject jo1 = mapper.select(jo);
        jo = new JSONObject();
        if (jo1.isEmpty()){
            jo.put("code","-1");
            jo.put("info","查询异常或者为空");
        } else {
            jo.put("coe","1");
            jo.put("info",jo1);
        }
        return jo;
    }*/

    /**
     * mybatis查询findAll
     * */
    @GetMapping(value = "/findAll")
    public JSONObject findAll(){
        List<Map<String,Object>> array = mapper.selectAll();
        JSONObject jo = new JSONObject();
        if (array.isEmpty()){
            jo.put("code","-1");
            jo.put("info","查询异常或者为空1");
        } else {
            jo.put("coe","1");
            jo.put("info",array);
        }
        return jo;
    }
}
