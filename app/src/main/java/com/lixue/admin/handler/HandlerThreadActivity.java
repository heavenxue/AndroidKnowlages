package com.lixue.admin.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lixue.admin.asmlifecycledemo.R;

public class HandlerThreadActivity extends AppCompatActivity {
    private HandlerThread thread;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);

        thread = new HandlerThread("handler thread");
        thread.start();

        handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("currentThread-----> " + Thread.currentThread());
            }
        };

        handler.sendEmptyMessage(1);
    }
}
