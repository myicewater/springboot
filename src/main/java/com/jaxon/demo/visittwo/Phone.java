package com.jaxon.demo.visittwo;

import com.jaxon.demo.visiteone.Animal;

public class Phone {

    public static void main(String[] args) {
        Animal animal = new Animal();//只能访问public 的方法或属性
        System.out.println(animal.publicStr);
        animal.publicMeth();
    }
}
