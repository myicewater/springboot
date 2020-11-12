package com.jaxon.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
@RequestMapping
public class BillAcceptController {

    @RequestMapping("acceptBill")
    @ResponseBody
    public String acceptBill(HttpServletRequest request){

        System.out.println("请求进来了");
        BufferedReader reader = null;
        try {
            reader = request.getReader();
        } catch (IOException e) {
            System.out.println("读取失败"+e);
        }
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            while((line =reader.readLine()) != null ){
                sb.append(line);
            }
        } catch (IOException e) {
            System.out.println("读取失败"+e);
        }



        System.out.println(sb.toString());

//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return "{\"rescode\":0,\"resmsg\":\"成功\"}";
    }


}
