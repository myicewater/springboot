package com.jaxon.demo.visiteone;

public class Cow extends  Animal{//类只能是单继承，

    public static void main(String[] args) {
        Cow cow = new Cow();
        //cow 可以访问Animal 除private之外的所有变量和方法
    }
}
