package com.lixue.admin.thread;

public class SynchronizedMethods {
//    private int sum = 0;
//    public synchronized void calculate(){
//        sum = sum + 1;
//    }

    private Object lock = new Object();

    public static void main(String[] args){
        SynchronizedMethods s1 = new SynchronizedMethods();
        SynchronizedMethods s2 = new SynchronizedMethods();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                s1.printLog();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                s2.printLog();
            }
        });
        t1.start();
        t2.start();
    }

    private void printLog() {
        synchronized (lock){
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " is printing" + i);
            }
        }
    }
}
