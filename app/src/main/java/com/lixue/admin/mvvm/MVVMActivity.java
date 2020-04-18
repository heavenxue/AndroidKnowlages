package com.lixue.admin.mvvm;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lixue.admin.asmlifecycledemo.R;
import com.lixue.admin.asmlifecycledemo.databinding.ActivityMVVMBinding;

public class MVVMActivity extends AppCompatActivity {
    private ActivityMVVMBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_v_v_m);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_m_v_v_m);
        MVVMViewModel mvvmViewModel = new MVVMViewModel();
        binding.setViewModel(mvvmViewModel);
    }
}
