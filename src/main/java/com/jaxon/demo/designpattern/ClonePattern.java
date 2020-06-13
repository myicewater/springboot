package com.jaxon.demo.designpattern;

public class ClonePattern  implements Cloneable{

    private  int age;
    private  String name;

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

    public ClonePattern(int age, String name){
        this.age = age;
        this.name = name;
    }

    @Override
    protected ClonePattern clone() throws CloneNotSupportedException {
        System.out.println("ClonePattern clone");
        return (ClonePattern)super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        ClonePattern a = new ClonePattern(78, "rtyu");
        ClonePattern b = a.clone();
        System.out.println(b.getName());
    }

}
