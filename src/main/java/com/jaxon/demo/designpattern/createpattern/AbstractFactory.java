package com.jaxon.demo.designpattern.createpattern;

public class AbstractFactory {

    public static void main(String[] args) {
        AbFactory f = new AFactory();
        f.init();
        Animal animal = f.animalProduce();
        animal.show();
        Fruits fruits = f.fruitProduce();
        fruits.show();
        f.finish();
    }
}

interface Animal{
    void show();
}
class Dog implements Animal{

    @Override
    public void show() {
        System.out.println("dog show");
    }
}
class Horse implements Animal{

    @Override
    public void show() {
        System.out.println("horse show");
    }
}

interface Fruits{
    void show();
}

class Apple implements Fruits{

    @Override
    public void show() {
        System.out.println("apple show");
    }
}

class Banana implements Fruits{

    @Override
    public void show() {
        System.out.println("banana show.");
    }
}

abstract class AbFactory{

    void init(){
        System.out.println("Produce start.");
    }

    abstract Animal animalProduce();

    abstract Fruits fruitProduce();

    void finish(){
        System.out.println("Finish produce.");
    }
}

class AFactory extends AbFactory{

    @Override
    Animal animalProduce() {
        return new Dog();
    }

    @Override
    Fruits fruitProduce() {
        return new  Apple();
    }


}

class BFactory extends AbFactory{

    @Override
    Animal animalProduce() {
        return new Horse();
    }

    @Override
    Fruits fruitProduce() {
        return new Banana();
    }
}
