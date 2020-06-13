package com.jaxon.demo.designpattern.abfactory;

public class BMWFactory extends Factory{

    @Override
    public Car produceCar() {
        return new BMW();
    }
}
