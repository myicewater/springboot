package com.jaxon.demo.thread;

import com.jaxon.demo.util.TimeUtil;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DoubleSynchroThread {


    private static ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(2,4,30l, TimeUnit.SECONDS,new LinkedBlockingDeque<>(2));

    /**
     * 双重异步
     */
    public static void doubleSynchroTest(){
        System.out.println(TimeUtil.getCurrentTime()+"外层异步处理start");
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                ThreadPoolExecutor tpe = new ThreadPoolExecutor(1,3,1l,TimeUnit.SECONDS,new LinkedBlockingDeque<>(1));
                //当队列满了会创建新的线程，最大数量不会超过 maximumPoolSize
                int activeCount = tpe.getActiveCount();
                long completedTaskCount = tpe.getCompletedTaskCount();
                int corePoolSize = tpe.getCorePoolSize();
                int largestPoolSize = tpe.getLargestPoolSize();
                int maximumPoolSize = tpe.getMaximumPoolSize();
                int poolSize = tpe.getPoolSize();
                long taskCount = tpe.getTaskCount();
                System.out.println(TimeUtil.getCurrentTime()+"执行前 activeCount:"+activeCount+" completedTaskCount:"+completedTaskCount+" corePoolSize:"+corePoolSize
                +" largestPoolSize:"+largestPoolSize+" maximumPoolSize:"+maximumPoolSize+" poolSize:"+poolSize+" taskCount:"+taskCount);

                MyTask myTask1 = new MyTask(2);
                MyTask myTask2 = new MyTask(3);
                MyTask myTask3 = new MyTask(4);
                Future<Object> submit = tpe.submit(myTask1);
                System.out.println(TimeUtil.getCurrentTime()+"task1 结果："+submit);
                Future<Object> submit1 = tpe.submit(myTask2);
                System.out.println(TimeUtil.getCurrentTime()+"task2 结果："+submit1);
                tpe.submit(myTask3);


                activeCount = tpe.getActiveCount();
                 completedTaskCount = tpe.getCompletedTaskCount();
                 corePoolSize = tpe.getCorePoolSize();
                 largestPoolSize = tpe.getLargestPoolSize();
                 maximumPoolSize = tpe.getMaximumPoolSize();
                 poolSize = tpe.getPoolSize();
                 taskCount = tpe.getTaskCount();

                System.out.println(TimeUtil.getCurrentTime()+"执行后 activeCount:"+activeCount+" completedTaskCount:"+completedTaskCount+" corePoolSize:"+corePoolSize
                        +" largestPoolSize:"+largestPoolSize+" maximumPoolSize:"+maximumPoolSize+" poolSize:"+poolSize+" taskCount:"+taskCount);

                //tpe.shutdown();

            }
        });

        System.out.println(TimeUtil.getCurrentTime()+"外层异步处理end");
    }

    public static void main(String[] args) {
        doubleSynchroTest();
    }
}
