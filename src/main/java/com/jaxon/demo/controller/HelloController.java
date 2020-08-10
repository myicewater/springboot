package com.jaxon.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @ResponseBody
    @RequestMapping("hello")
    public String sayHi(HttpServletRequest request,int age){
        String name = request.getParameter("name");
        int ma = Integer.valueOf(request.getParameter("age"));
        logger.info("年龄：{}",ma);
        logger.info("名称：{}",name);

        return "Hello ";
    }

    private static Map<String,Integer> rm = new HashMap();

    @ResponseBody
    @RequestMapping("httptest")
    public String httptest(HttpServletRequest request){
        Random random = new Random();
        String remoteHost = request.getRemoteHost();

        System.out.println("接受到请求:"+remoteHost);
        int time = random.nextInt(800);

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(rm.containsKey(remoteHost)){
            rm.put(remoteHost,rm.get(remoteHost)+1);
        }else{
            rm.put(remoteHost,1);
        }
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Integer>> entries = rm.entrySet();

        for(Map.Entry e : entries){
            sb.append("请求统计：");
            sb.append(e.getKey());
            sb.append(":");
            sb.append(e.getValue());
            sb.append(" , ");
        }
        System.out.println(sb.toString());

        return "Hello ";
    }



}
