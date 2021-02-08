package com.jaxon.demo.designpattern.structpattern;

public class ProxyPattern {

    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        proxy.foo();
    }

}

interface Foo{
    void foo();
}

class Boo implements Foo{

    @Override
    public void foo() {
        System.out.println("foo");

    }
}

class Proxy implements Foo{

    Foo foo;


    @Override
    public void foo() {
        if(foo == null){
            foo = new Boo();
        }
        foo.foo();

    }
}