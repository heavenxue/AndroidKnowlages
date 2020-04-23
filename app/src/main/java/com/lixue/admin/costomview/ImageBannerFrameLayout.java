package com.lixue.admin.costomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lixue.admin.asmlifecycledemo.R;

import java.util.List;

public class ImageBannerFrameLayout extends FrameLayout implements ImageBannerViewGroup.ImageBannerViewGroupListener, ImageBannerViewGroup.BannerListener {
    private ImageBannerViewGroup imageBannerViewGroup;
    private LinearLayout linearLayout;
    private ImageBannerFrameLayoutListener listener;

    public ImageBannerFrameLayout(@NonNull Context context) {
        super(context);
        initImageBannerViewGroup();
        initDotLinearLayout();
    }

    public ImageBannerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageBannerViewGroup();
        initDotLinearLayout();
    }

    public ImageBannerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageBannerViewGroup();
        initDotLinearLayout();
    }

    private void initImageBannerViewGroup() {
        imageBannerViewGroup = new ImageBannerViewGroup(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        imageBannerViewGroup.setLayoutParams(lp);
        imageBannerViewGroup.setViewGroupListener(this);
        imageBannerViewGroup.setBannerListener(this);
        addView(imageBannerViewGroup);

    }

    private void initDotLinearLayout(){
        linearLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 40);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);

        linearLayout.setBackgroundColor(Color.RED);
        addView(linearLayout);

        FrameLayout.LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(layoutParams);

        //在android3.0以后我们使用的是setAlpha(),在3.0之前，我们使用的是setAlpha()但是调用者不同
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            linearLayout.setAlpha(0.5f);
        }else{
            linearLayout.getBackground().setAlpha(50);
        }
    }

    public void addBitmaps(List<Bitmap> list){
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmap = list.get(i);
            addBitmapToImageBarnnerViewGroup(bitmap);
            addDotToLinearLayout();
        }
    }

    private void addDotToLinearLayout() {
        ImageView iv = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5,5,5,5);
        iv.setLayoutParams(lp);
        iv.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(iv);
    }

    private void addBitmapToImageBarnnerViewGroup(Bitmap bitmap) {
        ImageView imgView = new ImageView(getContext());
        imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getScreenWidth(), 300);
        imgView.setLayoutParams(params);
        imgView.setImageBitmap(bitmap);
        imageBannerViewGroup.addView(imgView);
    }

    private int getScreenWidth(){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    @Override
    public void selctImage(int index) {
        int count = linearLayout.getChildCount();
        for (int i = 0; i < count ; i++) {
            ImageView iv = (ImageView) linearLayout.getChildAt(i);
            if (i == index){
                iv.setImageResource(R.drawable.dot_select);
            }else {
                iv.setImageResource(R.drawable.dot_normal);
            }
        }
    }

    @Override
    public void click(int index) {
        if (listener != null)
            listener.onImgClick(index);
    }

    public interface ImageBannerFrameLayoutListener{
        void onImgClick(int pos);
    }

    public ImageBannerFrameLayoutListener getListener() {
        return listener;
    }

    public void setListener(ImageBannerFrameLayoutListener listener) {
        this.listener = listener;
    }
}
