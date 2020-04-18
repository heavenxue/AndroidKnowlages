package com.lixue.admin.databinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lixue.admin.asmlifecycledemo.R;
import com.lixue.admin.asmlifecycledemo.databinding.ActivityMvpBinding;

public class DataBindingActivity extends AppCompatActivity {
    private Account account;
    private ActivityMvpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_mvp);
        binding.tvInfo.setText("Test");
        account = new Account();
        account.setName("TEST");
        account.setLevel(100);
        //初始化account
        binding.setAccount(account);

        binding.setActivity(this);
    }

    public void onclick(View view){
        Toast.makeText(this,"点击了",Toast.LENGTH_SHORT).show();
        int level = account.getLevel();
        account.setLevel(level + 1);
//        binding.setAccount(account);Acount类中已经绑定了，所以会自动更新
    }
}
