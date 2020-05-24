package com.example.demo.json;

import com.alibaba.fastjson.JSONObject;

public class JsonTest {

    public static void main(String[] args) {
        JSONObject jsonObject = JSONObject.parseObject("{\"status\":\"S\",\"msg\":null,\"data\":null,\"errorCode\":0}");
        if(jsonObject == null )
            System.out.println("null");
    }
}
