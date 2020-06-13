package com.jaxon.demo.designpattern;

public class Singleton {
    /**
     * 私有的构造方法，方式外部调用
     */
    private Singleton(){}
    private static Singleton singleton = new Singleton();
    public static Singleton getSingleton(){
        return singleton;
    }
}
