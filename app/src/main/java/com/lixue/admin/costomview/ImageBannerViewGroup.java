package com.lixue.admin.costomview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义一个轮播图viewGroup
 */
public class ImageBannerViewGroup extends ViewGroup {

    private int childrenCount;
    private int childrenWidth;//每一张图片的宽度

    private int currentX;//每一次点击开始或者每一次移动过程中，移动之前的x的坐标
    private int imgIndex;//每一张图片的索引值

    private Scroller scroller;
    private BannerListener bannerListener;
    private ImageBannerViewGroupListener viewGroupListener;
    private boolean isClick;

    //==布局中小圆点
    /**
     * 要想实现图片轮播底部圆点以及底部圆点切换功能的步骤思路：
     * 1、我们需要自定义个继承自FrameLayout的布局，利用FramenLayout布局的特性（在同一位置放置不同的view最终显示的是
     * 最后一个view），我们就可以实现底部圆点的布局
     * 2、准备素材，底部圆点，我们可以利用drawable功能实现一个圆点图片的展示
     * 3、需要继承自FrameLayout来自定义一个类，在该类中，我们去加载刚才自定义的ImageBannerViewGroup和刚刚自定义的
     * */

    //==自动轮播
    /**
     * 我们会采用Timer,TimerTask,Handler三者相结合的方式来实现自动轮播
     * 我们会抽出两个方法来控制，是否启动自动轮播，sartAuto() stopAuto()
     * 那我们在这2个方法的控制过程中，我们实际上希望控制的是自动开启轮播图的开关
     * 那么我们就需要一个变量参数来作为我们自动开启轮播图的开关，我们称之为isAuto
     *
     * **/
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private boolean isAuto = true;//默认开启

    private Handler autoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://此时需要自动轮播

                    //如果滑动到最后一张图片了，让它从第一张图片开始
                    if (++imgIndex >= childrenCount){
                        imgIndex = 0;
                    }
                    scrollTo(childrenWidth * imgIndex,0);
                    //在这里要通知小圆点
                    viewGroupListener.selctImage(imgIndex);
                    break;
            }
        }
    };

    private void startAuto(){
        isAuto = true;
    }

    private void stopAuto(){
        isAuto = false;
    }

    public interface BannerListener{
        void click(int index);
    }

    public ImageBannerViewGroup(Context context) {
        super(context);
        initObj();
    }

    public ImageBannerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObj();
    }

    public ImageBannerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void initObj() {
        scroller = new Scroller(getContext());

        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isAuto){
                    autoHandler.sendEmptyMessage(0);
                }
            }
        };
        timer.schedule(timerTask,100,1000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //ViewGroup测量宽高，首先要知道子view宽高
        childrenCount = getChildCount();
        if (childrenCount == 0){
            setMeasuredDimension(0,0);
            return;
        }
        //测量子视图的宽度和高度
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //viewGroup的宽：第一个子view的宽 * 子view的个数
        //viewGroup的高：第一个子view的高
        View view = getChildAt(0);
        childrenWidth = view.getMeasuredWidth();
        int childrenHeight = view.getMeasuredHeight();
        setMeasuredDimension(childrenWidth * childrenCount,childrenHeight);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            int leftMargin = 0;
            for (int i = 0; i < childrenCount; i++) {
                View childrenView = getChildAt(i);
                childrenView.layout(leftMargin,0,leftMargin + childrenView.getMeasuredWidth(),childrenView.getMeasuredHeight());
                leftMargin += childrenView.getMeasuredWidth();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //拦截TouchEvent事件，这样就不会向子view传递事件，那么就会有此VieWGroup的TouchEvent方法执行
        return true;
    }

    /**
     * 有2种方式，实现轮播图的手动轮播
     * 1、利用ScrollTo ScrollBy完成轮播图的手动轮播
     * 2、利用Scroller对象完成轮播图的手动轮播
     *
     * 分析：
     *
     * 第一：我们在滑动屏幕图片的过程中，其实就是我们自定义ViewGroup的子视图的移动过程，那么我们只需要知道
     * 滑动之前的横坐标和滑动之后的横坐标，此时，我们就可以求出我们此过程中移动的距离，我们在利用scrollBy方法实现
     * 图片的滑动，所以我们需要2个值，移动之前和移动之后的横坐标的值
     *
     * 第二：在我们第一次按下的那一瞬间，此时移动之前和移动之后的值是相等的，也就是说，我们此时按下那一瞬间的那一个点的横坐标的值
     *
     * 第三：我们在不断的滑动过程中，是会不断的调用我们ACTIVON_MOVE方法，那么此时我们就应该将 移动之前的值 和 移动之后的值进行保存
     *
     * 第四： 在我们抬起的那一瞬间，我们需要计算出我们此时将要滑动到哪张图片的位置上
     *
     * 我们此时就需要求得出将要滑动的那张图片的索引值
     * （我们当前ViewGroup的滑动位置 + 我们的每一张图片的宽度 / 2） / 我们的每一张图片的宽度值
     * 此时我们就可以利用scrollTo方法，滑动到该图片的位置上
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isClick = true;
                //手指按下的那一刻停止自动轮播
                stopAuto();

                //优化
                //如果滑动还没有结束，此时我按下屏幕的那一瞬间，这个时候要让滑动停止
                if (!scroller.isFinished()){
                    scroller.abortAnimation();
                }

                currentX = (int) event.getX();
                System.out.println("onClick :" + currentX);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int distance = moveX - currentX;
                scrollBy(-distance,0);
                if (moveX != currentX){
                    isClick = false;
                }else {
                    isClick = true;
                }

                currentX = (int) event.getX();
                System.out.println("onMove :" + currentX);
                break;
            case MotionEvent.ACTION_UP:
                imgIndex = (getScrollX() + childrenWidth / 2)/ childrenWidth;
                if (imgIndex < 0){//说明此时已经滑动了最左边的第一张图片
                    imgIndex = 0;
                }else if (imgIndex > childrenCount -1){//说明此时已经胡奥东了最右边最后一张图片
                    imgIndex = childrenCount-1;
                }
                System.out.println("onUp :" + currentX);
                if (isClick){
                    bannerListener.click(imgIndex);
                }else {
//                scrollTo(imgIndex * childrenWidth,0);
                    int dx = imgIndex * childrenWidth - getScrollX();
                    scroller.startScroll(getScrollX(),0,dx,0);
                    postInvalidate();
                    //在这里要通知小圆点
                    viewGroupListener.selctImage(imgIndex);
                }

                //手指抬起的一瞬间继续自动轮播
                startAuto();

                break;
            default:
                break;
        }
        return true;//返回true，说明此事件被消费
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //还没有滑动完毕
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }

    public BannerListener getBannerListener() {
        return bannerListener;
    }

    public void setBannerListener(BannerListener bannerListener) {
        this.bannerListener = bannerListener;
    }

    public interface ImageBannerViewGroupListener{
        void selctImage(int index);
    }

    public ImageBannerViewGroupListener getViewGroupListener() {
        return viewGroupListener;
    }

    public void setViewGroupListener(ImageBannerViewGroupListener viewGroupListener) {
        this.viewGroupListener = viewGroupListener;
    }
}
