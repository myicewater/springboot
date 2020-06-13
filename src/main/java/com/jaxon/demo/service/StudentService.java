package com.jaxon.demo.service;

import com.jaxon.demo.entity.Student;

public interface StudentService {

    public Student getStudentById(int id);

    public void addStudent(Student student);

    public Student getStudent(int id);

}
