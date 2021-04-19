package com.jaxon.demo.array;

public class StringF {

    public static void main(String[] args) {
        String a = "fiefie";
        System.out.println(a.hashCode());;
        a+= "786";
        System.out.println(a.hashCode());


        StringBuffer s = new StringBuffer("fefea");
        s.append("tyui");
    }
}
