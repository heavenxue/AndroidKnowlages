package com.lixue.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lixue.admin.animation.Main2Activity;
import com.lixue.admin.asmlifecycledemo.MainActivity;
import com.lixue.admin.asmlifecycledemo.R;
import com.lixue.admin.costomview.ImageBannerActivity;
import com.lixue.admin.databinding.DataBindingActivity;
import com.lixue.admin.datastructure.AlgorithmActivity;
import com.lixue.admin.eventdispatch.EventDispatchActivity;
import com.lixue.admin.handler.HandlerThreadActivity;
import com.lixue.admin.imagesoptimization.LargeImagesActivity;
import com.lixue.admin.mvvm.MVVMActivity;
import com.lixue.admin.thread.ThreadActivity;

import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }
    @OnClick({R.id.btn_anim,R.id.btn_asmlifecycle,R.id.btn_customview,R.id.btn_databinding,R.id.btn_datastructure,R.id.btn_eventdispatch
    ,R.id.btn_handler,R.id.btn_picture,R.id.btn_mvvm,R.id.btn_thread})
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.btn_anim:
                startActivity(Main2Activity.class);
                break;
            case R.id.btn_asmlifecycle:
                startActivity(MainActivity.class);
                break;
            case R.id.btn_customview:
                startActivity(ImageBannerActivity.class);
                break;
            case R.id.btn_databinding:
                startActivity(DataBindingActivity.class);
                break;
            case R.id.btn_datastructure:
                startActivity(AlgorithmActivity.class);
                break;
            case R.id.btn_eventdispatch:
                startActivity(EventDispatchActivity.class);
                break;
            case R.id.btn_handler:
                startActivity(HandlerThreadActivity.class);
                break;
            case R.id.btn_picture:
                startActivity(LargeImagesActivity.class);
                break;
            case R.id.btn_mvvm:
                startActivity(MVVMActivity.class);
                break;
            case R.id.btn_thread:
                startActivity(ThreadActivity.class);
                break;
        }
    }
    private void startActivity(Class theclass){
        Intent intent = new Intent(HomeActivity.this, theclass);
        startActivity(intent);
    }
}