package com.example.demo.service.impl;

import com.example.demo.entity.Student;
import com.example.demo.exception.UserException;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Student getStudentById(int id) {
        if (id == 11) {
            throw new UserException(11,"不好听的编号");
        }

        return new Student(id,"lala");
    }

    @Override
    public void addStudent(Student student) {
        redisTemplate.opsForValue().set("STUDENT"+student.getId(),student);
    }

    @Override
    public Student getStudent(int id) {
        Object o1 = redisTemplate.opsForValue().get("STUDENT"+id);
        Student o = (Student)o1 ;
        return o;
    }
}
