package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Asuka on 2018/3/6.
 */
public class Util {

    public static String encode(String url){
        url = url.replaceAll("&","&gt;");
        url = url.replaceAll("<","&lt;");
        url = url.replaceAll(">","&gt;");
        url = url.replaceAll("\\s","&nbsp;");
        url = url.replaceAll("\'","&#39;");
        url = url.replaceAll("\"","&quot;");
        url = url.replaceAll("\\n","<br>");
        return url;
    }

    public static JSONObject success(){
        JSONObject jo = new JSONObject();
        jo.put("code","0");
        jo.put("result","success");
        return jo;
    }

    public static JSONObject fail(String msg){
        JSONObject jo = new JSONObject();
        jo.put("code","-1");
        jo.put("result",msg);
        return jo;
    }


}
