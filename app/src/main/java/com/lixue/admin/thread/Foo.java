package com.lixue.admin.thread;

public class Foo {
    private int number;
    public void test1(){
        int i = 0;
        synchronized (this){
            number = i + 1;
        }
    }
    public synchronized void test(){
        int i = 0;
        i = i + 1;
    }
}
