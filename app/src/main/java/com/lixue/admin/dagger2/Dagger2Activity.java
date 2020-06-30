package com.lixue.admin.dagger2;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lixue.admin.asmlifecycledemo.R;
import com.lixue.admin.dagger2.cook.Chef;
import com.lixue.admin.dagger2.cook.Menu;

import javax.inject.Inject;

public class Dagger2Activity extends AppCompatActivity {

    @Inject
    Chef chef;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
    }

    public void showCook(View view){
        Toast.makeText(this, chef.cook(), Toast.LENGTH_SHORT).show();
    }
}