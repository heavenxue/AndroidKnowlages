package com.lixue.admin.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lixue.admin.asmlifecycledemo.R;

public class ThreadOfHandlerActivity extends AppCompatActivity {
    private Thread myThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_handler);
        myThread = new MyThread();
        myThread.start();
    }

    class MyThread extends Thread{
        public Handler handler;

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Toast.makeText(getApplicationContext(),"mythread",Toast.LENGTH_SHORT).show();
                    Log.i("MyThread" ,"currentThread : " + Thread.currentThread());
                    System.out.println("currentThread: " + Thread.currentThread());
                }
            };

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
            Looper.loop();
        }
    }
}
