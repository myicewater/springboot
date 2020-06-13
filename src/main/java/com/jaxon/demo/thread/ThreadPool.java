package com.jaxon.demo.thread;

import java.util.concurrent.*;

public class ThreadPool {

    public void fixedThreadPoolTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        executorService.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void selfDefineThreadPool(){
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 50L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50));
        

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        System.out.println(executorService);

    }
}
