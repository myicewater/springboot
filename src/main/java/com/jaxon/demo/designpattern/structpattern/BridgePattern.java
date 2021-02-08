package com.jaxon.demo.designpattern.structpattern;

public class BridgePattern {

    public static void main(String[] args) {
        Executor e = new RealExecutor();
        Abstract a= new RealAbstract(e);
        a.operation();
    }
}

interface Executor{
    void execute();
}

class RealExecutor implements Executor{

    @Override
    public void execute() {
        System.out.println("真实执行者。。");
    }
}

abstract class Abstract{
    Executor excutor;
    public Abstract(Executor excutor){
        this.excutor = excutor;
    }

    abstract void operation();
}

class RealAbstract extends Abstract{

    public RealAbstract(Executor excutor){
        super(excutor);
    }

    @Override
    void operation() {
        System.out.println("扩展执行");
        excutor.execute();
    }
}