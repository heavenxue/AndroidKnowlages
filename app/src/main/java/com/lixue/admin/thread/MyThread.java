package com.lixue.admin.thread;

public class MyThread {

    public static void main(String[] args){
        int a,b;
        a = 1;
        b = 2;
        a = a + 1;
        System.out.println("a : " + a + ", b : " + b);
        //会发生指令重排，如下
        a = 1;
        a = a + 1;
        b = 2;
    }

    private int value = 0;

    public void setValue(int value) {
        synchronized(this) {
            this.value = 1;
        }
    }

    public int getValue() {
        synchronized(this) {
            return value;
        }
    }
}
