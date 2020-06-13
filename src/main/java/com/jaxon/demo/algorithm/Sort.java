package com.jaxon.demo.algorithm;

public class Sort {

    /**
     * 计数排序,整数类型，
     * @param arr
     */
    public static void countSort(int[] arr,int minV,int maxV){
        if(minV >= maxV){
            return;
        }
        int[] ca = new int[maxV-minV+1];
        for(int i=0;i<ca.length;i++){
            ca[i]=0;
        }
        printArray(ca);
        for(int i=0;i<arr.length;i++){
            ca[arr[i]-minV]++;
        }
        printArray(ca);
        int index =0;
        for(int i=0;i<ca.length;i++){
            while(ca[i]>0){
                arr[index] = i+minV;
                ca[i]--;
                index++;
            }
        }

    }

    /**
     * 平均时间复杂度 O(N2),
     * 冒泡排序，最大的值不停的往边上移动
     * @param arr
     */
    public static void bobbSort(int[] arr){
        int tmp =0;
        for(int i=0;i<arr.length-1;i++){
            for (int j = 0; j < arr.length - 1-i; j++) {
                if(arr[j] > arr[j+1]){
                    tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1]=tmp;
                }
            }
            printArray(arr);
        }
    }
    /**
     * 选择排序算法，时间复杂度 O(N2) 空间复杂度O(1)
     * @param source
     */
    public static void fastSort(int[] source){
        int tmp =0;
        for(int i=0;i<source.length-1;i++){
            for(int j=i+1;j<source.length;j++){
                if(source[i]>source[j]){
                    tmp = source[i]  ;
                    source[i] = source[j];
                    source[j] = tmp;
                }

            }
            printArray(source);
        }
    }

    public static void printArray(int[] arr){
        StringBuilder sb = new StringBuilder();
        for(int i:arr){
            sb.append(i);
            sb.append(" ");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        int[] arr = {5,2,1,4,5,9,3,6,2,1,7};
        countSort(arr,1,9);
        printArray(arr);
    }
}
