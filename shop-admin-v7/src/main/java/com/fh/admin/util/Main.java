package com.fh.admin.util;

public class Main {

    public static void main(String[] args) {
        //int i = 4;
        //System.out.println(i + "的阶乘是：" + jc(i));
        int m = 50;//46
        System.out.println("斐波那契数列的第"+m+"位:" + getFB(m));
    }


    public static Integer getFB(int n){
        if(n == 1 || n == 2){
        // 这里我们先保持前两位数是1
            return 1;
        }else {
            return getFB(n-1) + getFB(n-2);//getFB(n-1) + getFB(n-2)
        }
    }

    public static int jc(int i) {
        if (i == 0 && i == 1) {
            return 1;
        } else if (i > 0) {

            int result = i * jc(i - 1);
            return result;
        } else {
            return 1;
        }
    }

}
