package com.lixue.admin.thread;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    ReentrantLock lock = new ReentrantLock();

    public static void main (String[] args){
        ReentrantLockTest r1 = new ReentrantLockTest();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                r1.printLog();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                r1.printLog();
            }
        });
        t1.start();
        t2.start();
    }

    private void printLog() {
        try {
            lock.lock();
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " is printing " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
