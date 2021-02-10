package com.jaxon.demo.designpattern.behaviourpattern;

public class TemplatePattern {

    public static void main(String[] args) {
        Template t = new RealExecutor();
        t.templateMethod();

    }
}

/**
 * 模板方法定义了执行骨架，具体执行放在了子类中执行
 */
abstract class Template{
    void templateMethod(){
        specificMethod();
        abstractMethod1();
        abstractMethod2();
    }
    void specificMethod(){
        System.out.println("抽象类中的具体方法");
    }

    abstract void abstractMethod1();
    abstract void abstractMethod2();
}

class RealExecutor extends Template{

    @Override
    void abstractMethod1() {
        System.out.println("方法1执行");
    }

    @Override
    void abstractMethod2() {
        System.out.println("方法2执行");
    }
}
