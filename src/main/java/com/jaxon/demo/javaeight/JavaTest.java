package com.jaxon.demo.javaeight;

import java.util.*;

public class JavaTest {

    public static void main(String[] args) {

        System.out.println("HashMap------------------");
        Map m = new HashMap();
        m.put(null,null);
        System.out.println(m.size());
        System.out.println(1<<30);
        System.out.println(m.get(null));

        System.out.println("HashTable------------------");
        Map table = new Hashtable();
        table.put(null,null);
        System.out.println(table.get(null));

        System.out.println("HashSet------------------");
        Set  s = new HashSet();
        s.add(null);
        for(Object o : s){
            System.out.println(o);
        }
    }
}
