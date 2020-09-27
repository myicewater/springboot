package com.jaxon.demo.thread;

import com.jaxon.demo.util.TimeUtil;

import java.util.Random;
import java.util.concurrent.Callable;

class MyTask implements Callable<Object> {

    public MyTask(int index){
        this.index = index;
    }
    private int index;
    Thread thread ;

    @Override
    public Object call() throws Exception {
        thread = Thread.currentThread();
        System.out.println(TimeUtil.getCurrentTime()+"第二层任务 "+index+" 开始执行");
        try{

            Thread.sleep(index*1000);
            System.out.println(TimeUtil.getCurrentTime()+"第二层"+index+"执行任务");
        }catch (InterruptedException e){
            System.out.println(index+" 被中断了");

            return "except";
        }
        System.out.println(TimeUtil.getCurrentTime()+"第二层任务 "+index+" 执行结束");
        return index+" 成功";
    }

    public void setInterupt(){
        System.out.println("强行中断任务");
        this.thread.interrupt();
    }
}