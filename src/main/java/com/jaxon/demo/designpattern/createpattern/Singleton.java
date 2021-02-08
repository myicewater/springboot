package com.jaxon.demo.designpattern.createpattern;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Singleton {

    private Singleton(){};

    private static  Singleton singleton = new Singleton();

    /**
     * 恶汉式单例
     * @return
     */
    public static Singleton getSingleton(){

        return singleton;
    }

    public static void main(String[] args) {
        Singleton s1 = getSingleton();
        ExecutorService executorService = Executors.newFixedThreadPool(500);
        for (int i = 0; i <1000;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i <10000;i++){
                        Singleton s = getSingleton();
                        if(s1 != s){
                            System.out.println("不同的实例");
                        }
                    }

                }
            });
        }
        System.out.println("over.");
        executorService.shutdown();
    }
}
