package com.lixue.admin.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class BallView extends View {
    private float mRadius = 10;
    private  Point currentPoint;
    private Paint mPaint;
    private int width,height;

    public BallView(Context context) {
        super(context);
        init();
    }


    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaint = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        if (currentPoint == null) {
            startAnimationMotion();// 执行动画
        }
        mPaint.setColor(Color.RED);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
        mPaint.setColor(Color.MAGENTA);
        canvas.drawCircle(currentPoint.getX(), currentPoint.getY(), mRadius, mPaint);
    }

    /**
     * 小球动画
     */
    private void startAnimationMotion() {
        Point startPoint = new Point(mRadius, getHeight() / 2);
        Point endPoint = new Point(getWidth() - mRadius, 0);
        ValueAnimator animator = ValueAnimator.ofObject(new OscillationEvaluator(this), startPoint, endPoint);
        animator.setDuration(7000).setRepeatCount(3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());//设置插值器
        animator.start();
    }
}
