package com.jaxon.demo.thread;

public class DeadLock {

    Object a = new Object();
    Object b = new Object();

    void fooA(){
        synchronized(a){
            System.out.println(Thread.currentThread().getId()+" get a lock");
            synchronized(b){
                System.out.println(Thread.currentThread().getId()+" get a and b  lock");
            }

        }
    }

    void fooB(){

        synchronized(b){
            System.out.println(Thread.currentThread().getId()+" get b lock");
            synchronized(a){
                System.out.println(Thread.currentThread().getId()+" get a and b  lock");
            }
        }
    }


    public static void main(String[] args) {

        DeadLock dl = new DeadLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dl.fooA();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                dl.fooB();
            }
        }).start();

    }

}
