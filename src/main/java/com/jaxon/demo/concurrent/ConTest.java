package com.jaxon.demo.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ConTest {

//     static int num = 0;
    volatile static int num = 0;

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();


        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i =0;i<100;i++){
            executorService.submit(new Runnable() {

                @Override
                public void run() {


                    lock.lock();
                        num++;
                    System.out.println(num);
                    lock.unlock();



                }
            });
        }

        executorService.shutdown();

    }
}
