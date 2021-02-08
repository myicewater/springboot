package com.jaxon.demo.designpattern.createpattern;

public class FactoryPattern {

    public static void main(String[] args) {
        Factory factory = new F1();
        Product produce = factory.produce();
        produce.show();

        Factory f2 = new F2();
        Product produce1 = f2.produce();
        produce1.show();
    }
}

interface Factory{
     Product produce();
}

class F1 implements Factory{

    @Override
    public Product produce() {
        return new P1();
    }
}

class F2 implements Factory{

    @Override
    public Product produce() {
        return new P2();
    }
}

interface Product{
    void show();
}
class P1 implements Product{

    @Override
    public void show() {
        System.out.println("p1 produce");
    }
}

class P2 implements Product{

    @Override
    public void show() {
        System.out.println("p2 produce");
    }
}




