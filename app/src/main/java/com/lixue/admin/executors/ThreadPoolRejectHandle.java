package com.lixue.admin.executors;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRejectHandle {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,3,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(2));
        for (int i = 0; i <= 6 ; i++) {
            final int taskid = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程： " + Thread.currentThread().getName() +" 正在执行任务 " + taskid);
                    //任务耗时5秒
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("此时等待队列中有 " + threadPool.getQueue().size() + " 个元素");
        }

        threadPool.shutdown();
    }
}
