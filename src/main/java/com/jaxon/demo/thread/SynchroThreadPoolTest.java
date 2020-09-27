package com.jaxon.demo.thread;

import java.util.concurrent.*;

public class SynchroThreadPoolTest {


    static ThreadPoolExecutor executorService = new ThreadPoolExecutor(1,1,60l, TimeUnit.SECONDS,
            new SynchronousQueue<>(true));



    public static int timeOutTest(){

        System.out.println("开始当前活跃线程："+executorService.getActiveCount());

        MyTask myTask = new MyTask(5);
//        Future<Object> submit = executorService.submit(myTask);
         executorService.submit(myTask);
        System.out.println("提交后当前活跃线程："+executorService.getActiveCount());

//        try {
//            Object r = submit.get();//尽管会抛出超时异常，但是任务还是会执行完的
//            System.out.println("r"+ (String)r);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//
//            System.out.println("任务超时");
//
//            myTask.setInterupt();
//            System.out.println("中断后当前活跃线程："+executorService.getActiveCount());
//
//        }
        return 666;
    }

    public static void poolTest(final int index){

        while (true) {
            try{

                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(1000);
                            System.out.println(index+"执行完了");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                break;
            }catch (RejectedExecutionException e){
                System.out.println(index + "被拒绝了");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                continue;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("最大线程数："+Runtime.getRuntime().availableProcessors() * 10);
//        for(int i=0;i<100000;i++){
//            poolTest(i);
//        }
        int r = timeOutTest();
        System.out.println("提交后返回结果："+r);
        System.out.println("最后后当前活跃线程："+executorService.getActiveCount());
    }


}


