package com.jaxon.demo.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class AutomicIntegerTest {

    public int commonCount ;

    public AtomicInteger ai = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        AutomicIntegerTest at = new AutomicIntegerTest();
        CountDownLatch cd =  new CountDownLatch(100);
        for(int i=0;i< 100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    at.run();
                    cd.countDown();
                }
            }).start();
        }

        cd.await();
        System.out.println(at.commonCount + ","+at.ai.get());

        LongAdder la= new LongAdder();

    }


    public void run() {
        for (int i =0;i<1000;i++){
            commonCount++;
            ai.getAndIncrement();
        }
    }
}
