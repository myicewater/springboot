package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.example.demo.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentServiceTest {

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
