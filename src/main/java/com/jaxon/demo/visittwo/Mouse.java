package com.jaxon.demo.visittwo;

import com.jaxon.demo.visiteone.Animal;

public class Mouse extends Animal {

    public static void main(String[] args) {
       Mouse mouse = new Mouse();
       mouse.protectMeth();
       mouse.publicMeth();
       //可以访问受保护的和共有的
    }
}
