package com.example.demo.basecore;

import java.util.HashMap;
import java.util.Map;

public class Dog extends  Animal {

    @Override
    public void eat() {
        System.out.println("dog eat");
    }

    public static void main(String[] args) {
        Dog dog = new Dog();
        Map map = new HashMap();


        dog.eat();
    }
}
