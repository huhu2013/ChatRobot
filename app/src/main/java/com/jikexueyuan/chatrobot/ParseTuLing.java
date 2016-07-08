package com.jikexueyuan.chatrobot;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ParseTuLing {

    public static final String URL = "http://www.tuling123.com/openapi/api";
    public static String getJsonResult(String info){
        String result = "";
        Map<String,String> maps = new HashMap<>();
        maps.put("key","0ec2d9ca4b5a289250c71a0f85abe0db");
        maps.put("info",info);
        result = NetUtil.post(URL,maps);
        return result;
    }

    public static String parseData(String data){
        String jsonData = getJsonResult(data);
        String result = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            result = jsonObject.getString("text");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
