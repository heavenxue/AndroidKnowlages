package com.lixue.admin.imagesoptimization;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.lixue.admin.asmlifecycledemo.R;

import java.io.File;

public class PicturesActivity extends AppCompatActivity {
    private static final String TAG = PicturesActivity.class.getSimpleName();
    private Bitmap mCurrentBitmap;

    private ImageView firstImg;
    private ImageView secondImg;

    private Bitmap currentBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        firstImg = findViewById(R.id.img_first);
        secondImg = findViewById(R.id.img_second);
        getPermissions();

    }

    private void getPermissions(){
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_PERMISSION_STORAGE = 100;
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_PERMISSION_STORAGE);
                    return;
                }else {
                    loadOriginalSize(firstImg);
                    testPicOptimize(secondImg);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //申请权限成功
                loadOriginalSize(firstImg);
                testPicOptimize(secondImg);
            }else{
                //申请权限被拒绝
            }
        }
    }

    /**
     * 直接加载load sd卡里的图片
     * @param img
     */
    private void loadOriginalSize(ImageView img) {
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        String file = sdcard + File.separator + "11.jpg";

        try {
            currentBitmap = BitmapFactory.decodeFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        img.setImageBitmap(currentBitmap);
    }

    /**
     * 压缩图片
     * @param img
     */
    private void testPicOptimize(ImageView img){
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        String file = sdcard + File.separator + "11.jpg";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file,options);
        int width = options.outWidth;
        options.inSampleSize = width / 200;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(file,options);
        img.setImageBitmap(bitmap);
    }

    /**
     * inBitmap的使用(这样就是实现了，第二张图片复用了第一张图片的内存)
     * @param img
     */
    private void testInBitmap(ImageView img){
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        String file = sdcard + File.separator + "11.jpg";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inBitmap = currentBitmap;
        Bitmap bitmap = BitmapFactory.decodeFile(file,options);
        img.setImageBitmap(bitmap);

    }
}
