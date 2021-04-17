package com.jaxon.demo.sys;

public class SystemStu {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){

            @Override
            public void run() {
                System.out.println("系统结束了");
            }
        }));

        System.out.println("goodbye.");
        System.out.println("即将执行Hook");
    }
}
