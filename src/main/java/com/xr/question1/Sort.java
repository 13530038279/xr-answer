package com.xr.question1;

import java.util.Arrays;


public class Sort {
    public static int[] merge(int a[],int b[])
    {
        int result[];
        result = new int[a.length+b.length];
        //i:a数组下标    j：b数组下标  k：新数组下标
        int i=0,j=0,k=0;
        while(i<a.length && j<b.length){
            if(a[i] <= b[j]) {
                result[k++] = a[i++];
            }else{
                result[k++] = b[j++];
            }
        }
 
        /* 后面连个while循环是用来保证两个数组比较完之后剩下的一个数组里的元素能顺利传入 *
         * 此时较短数组已经全部放入新数组，较长数组还有部分剩余，最后将剩下的部分元素放入新数组，大功告成*/
        while(i < a.length){
            result[k++] = a[i++];
        }
 
        while(j < b.length){
            result[k++] = b[j++];
        }
 
        return result;
    }
    public static void main(String[] args) {
    	Sort test = new Sort();
        int[] num1 = {1,3,4,6};
        int[] num2 = {2,5,7,8};
        int result[] = test.merge(num1,num2);
        System.out.println(Arrays.toString(result));
 
    }
}
