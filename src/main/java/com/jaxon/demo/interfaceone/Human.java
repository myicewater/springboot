package com.jaxon.demo.interfaceone;

public interface Human {
    /**
     *总结:
     * 1.接口中的变量默认都是 pubilc static final的，不能继承，只能通过类访问
     * 2.接口中的方法默认都是public abstract的
     *
     * 类或接口都可以有static静态变量或方法，静态的只能通过类或接口访问
     *
     */
    String staticVar = "";
    String gen = "";
    static void staticMeth(){

    }

    public abstract void publicMeth();


    void defaultMeth();

    /**
     * 扩展方法，可以重新，可以继承
     */
    default void obDefaultMeh(){
        System.out.println("human breath");
    }
}
