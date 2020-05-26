package com.example.demo.file;

import java.io.File;

public class FileDir {
    
    public static void dir(String fileDir){
        File file = new File(fileDir);
        if(file.isFile()){
            System.out.println(file.getPath());
        }
        if(file.isDirectory()){
            System.out.println("====="+file.getPath());
            File[] files = file.listFiles();
            for(File f : files){
                dir(f.getPath());
            }
        }
    }

    public static void main(String[] args) {
        dir("D:\\home");

    }
}
