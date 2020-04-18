package com.lixue.admin.eventdispatch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class MyViewGroup extends FrameLayout {
    public MyViewGroup(@NonNull Context context) {
        super(context);
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(EventDispatchActivity.LOG,"MyViewGroup : dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(EventDispatchActivity.LOG,"MyViewGroup : onInterceptTouchEvent");
        super.onInterceptTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(EventDispatchActivity.LOG,"MyViewGroup : onTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(EventDispatchActivity.LOG,"MyViewGroup : 手指按下");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(EventDispatchActivity.LOG,"MyViewGroup : 手指移动");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(EventDispatchActivity.LOG,"MyViewGroup : 手指抬起");
                break;
        }
        super.onTouchEvent(event);
        return true;
    }
}
