package com.jaxon.demo.interfaceone;

public abstract class Car {

    private int privateVar = 10;

    protected String protectedVar = "";
     String defaultVar = "";
     public  String publicVar = "";
     private static int staticVAr = 0;

     public static int staticPubVar = 5;

     void defaultMeth(){};

     private void privateMeth(){}

     public abstract void publicMeth();

    public static void main(String[] args) {
        Car car = new Car() {
            @Override
            public void publicMeth() {

            }
        };

    }
}
