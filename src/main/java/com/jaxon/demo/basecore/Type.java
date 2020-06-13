package com.jaxon.demo.basecore;

public class Type {

    /**
     * base type：byte shot int long float double char boolean
     */

    public static void shotTest(){
        short a = 3;
        a++;
        //a =a+ 1; 编译失败
        a+= 1;
        System.out.println(a);
    }

    public static void charTest(){
        char a = 'a';

        System.out.println(a);
    }

    public static void StringTest(){
        String a = "a";
        StringBuilder sb = new StringBuilder();
        sb.append("a");
        StringBuffer sbf = new StringBuffer();

    }

    public static void main(String[] args) {
        //shotTest();
        charTest();
    }
}
