package com.jaxon.demo.designpattern.createpattern;

import com.jaxon.demo.entity.Student;



public class ClonePattern  implements Cloneable{

    private  int age;
    private  String name;
    Student  s;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student getS() {
        return s;
    }

    public void setS(Student s) {
        this.s = s;
    }

    public ClonePattern(int age, String name){
        this.age = age;
        this.name = name;
    }

    /**
     * 浅克隆
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected ClonePattern clone() throws CloneNotSupportedException {
        System.out.println("ClonePattern clone");
        return (ClonePattern)super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        ClonePattern a = new ClonePattern(78, "rtyu");
        Student s = new Student(34,"uuu");
        a.setS(s);

        ClonePattern b = a.clone();
        System.out.println(b.getName());
        System.out.println(a.getS() == b.getS());
    }

}


