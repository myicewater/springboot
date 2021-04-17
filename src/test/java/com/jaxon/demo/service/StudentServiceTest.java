package com.jaxon.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.jaxon.demo.entity.Student;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


public class StudentServiceTest extends BaseTest{

    @Autowired
    private StudentService studentService;

    @Test
    public void addStudentTest(){
        Student student = new Student(501,"Jaxon");
        studentService.addStudent(student);


    }

    @Test
    public void getStudent(){
        Student student1 = studentService.getStudent(501);
        System.out.println(JSONObject.toJSONString(student1));
    }
}
