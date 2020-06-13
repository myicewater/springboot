package com.jaxon.demo.designpattern.abfactory;

public class FactoryProduce {

    public static Factory produce(String type){
        if ("toyota".equals(type)) {
            return new ToyotaFactory();
        }
        if ("BMW".equals(type)) {
            return  new BMWFactory();        }
        return null;
    }

    public static void main(String[] args) {
        ToyotaFactory factory = (ToyotaFactory)produce("toyota");
        Toyota car = (Toyota)factory.produceCar();
        car.start();

    }
}
