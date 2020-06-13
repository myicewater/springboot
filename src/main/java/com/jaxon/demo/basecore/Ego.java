package com.jaxon.demo.basecore;

public class Ego implements Fly {
    @Override
    public void flying() {
        System.out.println("I'm ego flying");
    }

    public static void main(String[] args) {
        Ego ego = new Ego();

        ego.flying();
        ego.height();

        Fly fly = new Fly() {
            @Override
            public void flying() {
                System.out.println("interface flying");
            }
        };

        fly.height();
    }
}
