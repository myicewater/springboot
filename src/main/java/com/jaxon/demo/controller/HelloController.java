package com.jaxon.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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



}
