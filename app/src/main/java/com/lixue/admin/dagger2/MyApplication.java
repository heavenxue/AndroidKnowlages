package com.lixue.admin.dagger2;

import android.app.Application;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DaggerCookAppComponent.builder().application(this).build().inject(this);
//        BreakpadInit.initBreakpad("breakpad/c/log");
    }
}
