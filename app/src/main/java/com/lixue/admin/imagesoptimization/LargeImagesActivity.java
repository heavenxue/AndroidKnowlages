package com.lixue.admin.imagesoptimization;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lixue.admin.asmlifecycledemo.R;

import java.io.IOException;

public class LargeImagesActivity extends AppCompatActivity {
    private LargeImageView largeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_images);
        largeImg = findViewById(R.id.largeImg);
        try {
            largeImg.setImage(getResources().getAssets().open("timg.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
