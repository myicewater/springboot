package com.jaxon.demo.designpattern.structpattern;

public class DecoratorPattern {

    public static void main(String[] args) {
        RealComponent real = new RealComponent();
        RealDecorator realdecorator = new RealDecorator(real);
        realdecorator.operation();
    }
}

interface Component{
    void operation();
}

class RealComponent implements Component{

    public RealComponent(){
        System.out.println("实际组件构造");
    }
    @Override
    public void operation() {
        System.out.println("实际组件执行");
    }
}

/**
 * 装饰模式的关键在于装饰角色 实现了组件接口，并且关联了组件，通过构造方法来构造
 *
 * 附加装饰还可以增加自身特有方法执行
 */
class Decorator implements Component{

    Component component;
    public Decorator(Component component){
        this.component = component;
    }
    @Override
    public void operation() {
        component.operation();
    }
}
class RealDecorator extends Decorator{

    public RealDecorator(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        super.operation();
        specificOperation();
    }
    void specificOperation(){
        System.out.println("特殊执行方法");
    }
}
