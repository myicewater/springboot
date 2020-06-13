package com.jaxon.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("common")
@Controller
public class CommonController {


    @RequestMapping("fail")
    @ResponseBody
    public String fail(){
        return "invalid request";
    }

    @RequestMapping("invalid")
    public String invalidRequest(){
        return null;
    }
}
