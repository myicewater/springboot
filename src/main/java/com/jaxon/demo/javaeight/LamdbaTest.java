package com.jaxon.demo.javaeight;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class LamdbaTest {

    public static void main(String[] args) {

        Operation add = (a,b)->{
            System.out.println("count:"+a+"+"+"b");
            return a+b;
        };
        Operation minus =(a,b)->a-b;

        System.out.println(add.operate(5,10));
        LamdbaTest lamdba = new LamdbaTest();
        int a = 500;
        System.out.println(lamdba.operate(a,23,minus));

        List<String> strings = Arrays.asList("a", "b");
        for(String s : strings){
            System.out.println(s);
        }//两个作用相同
        strings.forEach(e-> lamdba.suerPrintString(e));

        Collection<String> ss = Arrays.asList("a","b");
        ss.stream();

    }

    void suerPrintString(String s){
        System.out.println("This is super print:"+s);
    }

    int operate(int a,int b,Operation operater){
        return operater.operate(a,b);
    }
}

interface Operation{
    int operate(int a ,int b);


}
