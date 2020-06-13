package com.jaxon.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.jaxon.demo.entity.ApiResult;
import com.jaxon.demo.entity.Student;
import com.jaxon.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private StudentService studentService;

    @ResponseBody
    @RequestMapping("getStudentById")
    public String getStudentById(int id) throws Exception{
        Student studentById = studentService.getStudentById(id);
        System.out.println(JSONObject.toJSONString(studentById));
        ApiResult success = ApiResult.success(studentById);
        return JSONObject.toJSONString(success);
    }

    @ResponseBody
    @RequestMapping("addStudent")
    public String addStudent(int id, String name) {
        Student s = new Student(id,name);
        studentService.addStudent(s);
        ApiResult success = ApiResult.success(id);
        return JSONObject.toJSONString(success);
    }
}
