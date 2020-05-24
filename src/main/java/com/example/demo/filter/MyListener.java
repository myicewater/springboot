package com.example.demo.filter;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("监听器销毁");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("监听器启动");
    }
}
