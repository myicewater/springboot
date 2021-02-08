package com.jaxon.demo.designpattern.structpattern;

public class AdapterPattern {

    public static void main(String[] args) {

        Target target = new Adater();
        target.targetMethod();
    }
}

interface Target{
    void targetMethod();
}

class TargetExecutor{
    void specificMethod(){
        System.out.println("特殊方法执行");
    }
}

class Adater extends TargetExecutor implements Target{

    @Override
    public void targetMethod() {
        specificMethod();
    }
}