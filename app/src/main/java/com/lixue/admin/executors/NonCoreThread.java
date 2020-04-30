package com.lixue.admin.executors;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NonCoreThread {
    public static void main(String[] args) {
        //核心线程为2，最大线程数为10，等待队列长度为2
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,10,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(2));
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程： " + Thread.currentThread().getName() + "正在执行 task: "+ taskId);
                    //任务耗时10秒
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("此时等待队列中有 " + threadPoolExecutor.getQueue().size() + " 个元素");
        }
        threadPoolExecutor.shutdown();
    }
}
