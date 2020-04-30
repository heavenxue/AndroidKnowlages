package com.lixue.admin.executors;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CreateScheduledThreadPool {
    public static void main(String[] args) {
        //指定线程数量为2的定时任务线程池
        ScheduledExecutorService sheduledThreadPool = Executors.newScheduledThreadPool(2);
        //延迟500毫秒执行，每隔500毫秒执行一次
        sheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Date now = new Date();
                System.out.println("线程：" + Thread.currentThread().getName() + " 报时： " + now);
            }
        },500,500, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //使用shutdow关闭定时任务
        sheduledThreadPool.shutdown();
    }
}
