package com.lixue.admin.thread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lixue.admin.asmlifecycledemo.R;

public class ThreadActivity extends AppCompatActivity {

    private int x = 0;
    private int y =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        Thread p1 = new Thread(){
            @Override
            public void run() {
                int r1 = x;
                y = 1;
            }
        };

        Thread p2 = new Thread(){
            @Override
            public void run() {
                int r2 = y;
                x = 2;
            }
        };

//        Thread p2 = new Thread(){
//            @Override
//            public void run() {
//                x = 2;
//                int r2 = y;
//            }
//        };

        p1.start();
        p2.start();
    }
}
