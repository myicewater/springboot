package com.jaxon.demo.designpattern.abfactory;

public abstract class Factory {

    public abstract Car produceCar();

    public void prepare(){
        System.out.println("prepare produce");
    }
}
