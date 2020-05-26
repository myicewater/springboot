package com.example.demo.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class FileRead {

    public static void ioRead(String filePath)  {

        long start = System.currentTimeMillis();
        File file = new File(filePath);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[1024];
        try{

            int count = fileInputStream.read(buffer);

            while(count != -1){
                for(int i=0;i<count;i++){
                    System.out.println((char)buffer[i]);
                }
                count = fileInputStream.read(buffer);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        System.out.println("ioread cost:"+(end-start));
    }

    public static  void bufferRead(String filePath) {
        long start = System.currentTimeMillis();

        File file = new File(filePath);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try{

            String s = bufferedReader.readLine();
            while(s != null){
                System.out.println(s);
                s = bufferedReader.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        System.out.println("bufferRead cost:"+(end-start));
    }

    public static void nioFileRead(String filePath) throws Exception{

        long start = System.currentTimeMillis();
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath,"rw");

        FileChannel channel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = channel.read(byteBuffer);
        while(read != -1){

            byteBuffer.flip();//position 回到0，limit 设置为position位置
            while(byteBuffer.hasRemaining()){
                System.out.println((char)byteBuffer.get());
            }

            byteBuffer.compact();//将未读的字符copy到buffer起始处，将position设置为未读最后一个位置
            read = channel.read(byteBuffer);
        }

        long end = System.currentTimeMillis();

        System.out.println("nioFileRead cost:"+(end-start));

    }

    public static void main(String[] args) throws Exception {
        nioFileRead("D:\\HostKeyDB.txt");
    }
}
