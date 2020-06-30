package com.lixue.admin.dagger2;

import com.lixue.admin.asmlifecycledemo.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityModules {
    @ContributesAndroidInjector
    abstract Dagger2Activity contributeDagger2Activity();
}
