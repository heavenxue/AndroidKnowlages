package com.lixue.admin.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CreateFixThreadPool {
    public static void main(String[] args) {
        //创建线程数量为2的线程池
        ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        //提交10个任务交给线程池执行
        for (int i = 0; i <= 5 ; i++) {
            final int taskId = i;
            fixedThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程： " + Thread.currentThread().getName() + " 正在执行 task: " + taskId);
                    try {
                        //线程耗时4秒
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("此时等待队列中有 " + fixedThreadPool.getQueue().size());
            try {
                //每500毫秒向线程池中提交任务
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fixedThreadPool.shutdown();
    }
}
