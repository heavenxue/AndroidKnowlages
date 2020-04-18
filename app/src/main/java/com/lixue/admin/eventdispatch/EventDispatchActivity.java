package com.lixue.admin.eventdispatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.lixue.admin.asmlifecycledemo.R;

/**
 * 1、验证事件传递流程
 * 2、验证cancle事件
 * 3、验证ViewGroup的事件拦截
 */
public class EventDispatchActivity extends AppCompatActivity {
    public static String LOG = "EventDispatchDemo";


    /**
     * 事件默认传递流程：
     * EventDispatchActivity:dispatchTouchEvent -> MyViewGroup:dispatchTouchEvent
     * -> MyViewGroup:onInterceptTouchEvent -> MyView:dispatchTouchEvent
     * -> MyView:onTouchEvent(默认的返回值为false,所以向上传递) ->MyViewGroup:onTouchEvent(默认的返回值为false,所以向上传递)
     * -> EventDispatchActivity：onTouchEvent
     *
     * 同一个事件序列，如果子View（ViewGroup）没有处理该事件（没有消费事件），
     * 那么后续事件就不会再传递到子view中
     *EventDispatchActivity:dispatchTouchEvent -> EventDispatchActivity：onTouchEvent
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dispatch);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(LOG,"EventDispatchActivity : dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(LOG,"EventDispatchActivity : onTouchEvent");
        return super.onTouchEvent(event);
    }
}
