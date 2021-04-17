package com.jaxon.demo.subtable;

import com.rabbitmq.tools.json.JSONUtil;

import java.util.UUID;

public class IdentityGenerate {

    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
        System.out.println(uuid);
        int i = uuid.hashCode();
        System.out.println(i);

        System.out.println(i%5);
    }



}
