package com.lixue.admin.costomview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lixue.admin.asmlifecycledemo.R;

import java.util.ArrayList;
import java.util.List;

public class ImageBannerActivity extends AppCompatActivity {
    private ImageBannerFrameLayout mFragment;
    int[] imgIds = {R.drawable.ad0,R.drawable.ad1,R.drawable.ad2,R.drawable.ad3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_banner);
        mFragment = findViewById(R.id.imgBanner);

        mFragment.setListener(new ImageBannerFrameLayout.ImageBannerFrameLayoutListener() {
            @Override
            public void onImgClick(int pos) {
                System.out.println("当前的pos是：" + pos);
                Toast.makeText(ImageBannerActivity.this,"pos: " + pos,Toast.LENGTH_SHORT).show();
            }
        });

        List<Bitmap> list = new ArrayList<>();

        for (int i = 0; i < imgIds.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imgIds[i]);
            list.add(bitmap);
        }
        mFragment.addBitmaps(list);


    }


}
