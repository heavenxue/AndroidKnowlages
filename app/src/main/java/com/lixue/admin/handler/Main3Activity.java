package com.lixue.admin.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lixue.admin.asmlifecycledemo.R;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private TextView textshow;
    private int[] imgs = {R.mipmap.img_0,R.mipmap.img_1,R.mipmap.img_2};
    private MyRunnable myRunnable = new MyRunnable();
    private int index;

//    private Handler myHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            textshow.setText("" + msg.arg1);
//        }
//    };
    private Handler myHandler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(Message message) {
        //返回true就拦截，返回false的话，就不拦截，下面的handleMessage方法也可以被执行
        Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
        return true;
    }
}){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_SHORT).show();
    }
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        imageView = findViewById(R.id.imageshow);
        textshow = findViewById(R.id.textshow);
        textshow.setOnClickListener(this);
        myHandler.postDelayed(myRunnable,1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Message message = new Message();
                    message.arg1 = 88;
                    myHandler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onClick(View view) {
        myHandler.sendEmptyMessage(1);
    }

    public class MyRunnable implements Runnable {

        @Override
        public void run() {
            index ++;
            index = index % 3;
            imageView.setImageResource(imgs[index]);
            myHandler.postDelayed(myRunnable,1000);
        }
    }
}
