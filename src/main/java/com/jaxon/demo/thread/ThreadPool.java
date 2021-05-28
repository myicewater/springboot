package com.jaxon.demo.thread;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    static class Task implements Runnable{
        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                System.out.println(name+" 执行完了");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        System.out.println(executorService);

        ThreadPoolExecutor tpe = new ThreadPoolExecutor(3, 5, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("拒绝了,活跃："+executor.getActiveCount());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executor.submit(r);

            }
        });

        for(int i=0;i< 12;i++){
            tpe.submit(new Task(""+i));
            System.out.println("active:"+tpe.getActiveCount());
        }

        ReentrantLock r;

        ReentrantReadWriteLock rr;

        Runtime runtime = Runtime.getRuntime();

        CopyOnWriteArrayList cw;
    }
}
