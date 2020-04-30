package com.lixue.admin.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateSingleThreadPool {
    public static void main(String[] args) {
        //创建单线程池
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i <= 5 ; i++) {
            final int taskid = i;
            //向线程池中提交任务
            singleThreadExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程：" + Thread.currentThread().getName() + " 正在执行 task:" + taskid);
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
