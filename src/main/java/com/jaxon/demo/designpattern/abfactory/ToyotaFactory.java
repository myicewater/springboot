package com.jaxon.demo.designpattern.abfactory;

public class ToyotaFactory extends Factory{
    @Override
    public Car produceCar() {
        return new Toyota();
    }
}
