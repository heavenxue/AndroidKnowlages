package com.lixue.admin.thread;

import java.util.concurrent.locks.ReentrantLock;

public class FairLockTest implements Runnable {
    private int sharedNumber = 0;
    //创建公平锁
    private static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        while (sharedNumber < 20){
            lock.lock();
            try {
                sharedNumber ++;
                System.out.println(Thread.currentThread().getName() + " 获得锁，sharedNumber is " + sharedNumber);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args){
        FairLockTest ft = new FairLockTest();
        Thread t1 = new Thread(ft);
        Thread t2 = new Thread(ft);
        Thread t3 = new Thread(ft);
        t1.start();
        t2.start();
        t3.start();
    }
}
