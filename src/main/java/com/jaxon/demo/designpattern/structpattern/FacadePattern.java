package com.jaxon.demo.designpattern.structpattern;

public class FacadePattern {

    public static void main(String[] args) {
        Facade f = new Facade();
        f.facade();
    }
}

/**
 * 门面模式说白了就是多个一次执行多个类的方法
 */
class Facade{
    private Sys1 sys1;
    private Sys2 sys2;

    void facade(){
        sys1.foo();
        sys2.foo();
    }
}

class Sys1{
    void foo(){
        System.out.println("system 1");
    }
}

class Sys2{
    void foo(){
        System.out.println("System 2");
    }
}
