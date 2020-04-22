package com.lixue.admin.imagesoptimization;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.io.IOException;
import java.io.InputStream;

public class LargeImageView extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {
    private final Rect mRect;  //需要显示的区域
    private final BitmapFactory.Options mOptions;  //内存复用
    private final GestureDetector mGestureDetector;  //手势操作
    private final Scroller mScroller;  //滑动类
    private int mImageWith;   //图片宽度
    private int mImageHeight;  //图片高度
    private BitmapRegionDecoder mDecoder; //区域解码器
    private int mViewWidth;  //控件宽度
    private int mViewHeight;  //控件高度
    private float mScale;  //图片缩放因子
    private Bitmap mBitmap;  //需要展示的图片（是被复用的）

    public LargeImageView(Context context) {
        this(context,null);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //第一步设置LargeImageView所需要的一些成员变量
        mRect = new Rect(); //矩形区域
        mOptions = new BitmapFactory.Options(); //内存复用
        mGestureDetector = new GestureDetector(context,this); //手势识别
        mScroller = new Scroller(context); //滚动类,如果用到Scroller这个类，computeScroll这个方法
        setOnTouchListener(this); //设置监听
    }

    //第二步,设置图片，得到图片信息
    public void setImage(InputStream is){
        //获取图片宽和高，注意不能将图片整个加载进内存
        mOptions.inJustDecodeBounds = true;  //设置仅加载图片的宽高信息不将图片加载的内存中
        BitmapFactory.decodeStream(is,null,mOptions);
        mImageWith = mOptions.outWidth;
        mImageHeight = mOptions.outHeight;

        mOptions.inMutable = true; //开启复用
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;  //设置解码模式（质量压缩），设置格式为RGB565(相比ARGB_8888去掉了透明通道，消耗内存少，但是图片质量会降低一些)
        mOptions.inJustDecodeBounds = false; //消除仅加载图片的宽高


        try {
            mDecoder = BitmapRegionDecoder.newInstance(is,false);//区域解码器
        } catch (IOException e) {
            e.printStackTrace();
        }

        requestLayout(); //刷新
    }

    //第三步，开始测量，得到view的宽高保存在Rect中，测量加载的图片缩放成什么样子
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //得到view的宽高
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        //确定加载图片的区域
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = mImageWith;
        //计算缩放因子
        mScale = mViewWidth/(float)mImageWith;
        mRect.bottom = (int) (mViewHeight/mScale);
    }

    //第四步，画出具体的内容
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //判断解码器是不是为null，如果解码器没有拿到，表示没有设置图片
        if(mDecoder == null){
            return;
        }
        //真正内存复用 注意复用的bitmap必须要跟即将解码的bitmap尺寸一样
        mOptions.inBitmap = mBitmap;  //绑定一个已经加载的Bitmap对象
        //指定解码区域
        mBitmap = mDecoder.decodeRegion(mRect,mOptions);
        //得到一个矩阵进行缩放，相当于得到的view的大小
        Matrix matrix = new Matrix();
        matrix.setScale(mScale,mScale);
        canvas.drawBitmap(mBitmap,matrix,null);
    }

    //第五步，处理点击事件
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //直接将事件交给手势事件处理
        return mGestureDetector.onTouchEvent(event);
    }

    //第六步，处理手按下去
    @Override
    public boolean onDown(MotionEvent e) {
        //如果移动没有停止，强行停止
        if(!mScroller.isFinished()){
            mScroller.forceFinished(true);
        }
        //继续接收后续事件
        return true;
    }

    //第七步，处理滑动事件
    //e1:开始事件，手按下去，开始获取坐标
    //e2:获取当前事件坐标
    //xy:xy轴移动的距离
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //上下移动的时候，mRect需要改变显示的区域
        mRect.offset(0, (int) distanceY);
        //移动时，处理到达顶部和底部的情况
        if (mRect.bottom > mImageHeight){
            mRect.bottom = mImageHeight;
            mRect.top = mImageHeight - (int)(mViewHeight / mScale);
        }
        if (mRect.top < 0){
            mRect.top = 0;
            mRect.bottom = (int)(mViewHeight / mScale);
        }
        invalidate();//重绘
        return false;
    }

    //第八步，处理惯性问题
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //注意：手势滑动方法onFling中的velocityY参数与Scroller.fling方法的参数velocityY方向是反的
        mScroller.fling(0,mRect.top,0,(int)-velocityY,0,0,0,
                mImageHeight-(int)(mViewHeight/mScale));
        return false;
    }

    //第九步，处理计算结果
    @Override
    public void computeScroll() {
        if(mScroller.isFinished()){
            return;
        }
        //要判断是否滑动完毕）如果没有滑动完毕，就继续加载
        if(mScroller.computeScrollOffset()){
            mRect.top = mScroller.getCurrY();
            mRect.bottom = mRect.top + (int)(mViewHeight / mScale);
            invalidate();//重绘
        }
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }


    @Override
    public void onLongPress(MotionEvent e) {

    }

}
