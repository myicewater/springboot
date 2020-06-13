package com.jaxon.demo.basecore;

public interface Fly {

    public static int win = 5;

    void flying();

    default void height(){
        System.out.println("Fly's height");
    }



}
