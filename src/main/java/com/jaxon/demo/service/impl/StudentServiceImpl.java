package com.jaxon.demo.service.impl;

import com.jaxon.demo.entity.Student;
import com.jaxon.demo.exception.UserException;
import com.jaxon.demo.service.StudentService;
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
            throw new UserException(1,"不好听的编号");
        }
        Object o1 = redisTemplate.opsForValue().get("STUDENT"+id);
        Student o = (Student)o1 ;

        return o;
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
