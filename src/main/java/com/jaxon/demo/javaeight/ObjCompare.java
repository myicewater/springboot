package com.jaxon.demo.javaeight;

public class ObjCompare {

    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }



    public static void main(String[] args) {
        Student s = new Student();
        ObjCompare o1 = new ObjCompare();
        ObjCompare o2 = new ObjCompare();
        Student[] ss = new Student[] { s };
        o1.setStudent(s);
        o2.setStudent(s);
        o2.getStudent().setAge(20);

        System.out.println(o1.getStudent() == o2.getStudent() );
        System.out.println(o1.getStudent() == ss[0] );
        System.out.println(o2.getStudent() == s );
        System.out.println(ss[0].getAge());
    }
}
class Student{

    int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}