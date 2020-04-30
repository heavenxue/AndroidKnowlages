package com.lixue.admin.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateCacheThreadPool {
    public static void main(String[] args) {
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i <= 5 ; i++) {
            final int taskid = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cacheThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程：" + Thread.currentThread().getName() + " 正在执行任务 task: " + taskid);
                    try {
                        //任务耗时500毫秒
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        cacheThreadPool.shutdown();
    }
}
