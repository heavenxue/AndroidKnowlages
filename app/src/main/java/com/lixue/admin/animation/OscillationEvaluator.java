package com.lixue.admin.animation;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * 等幅振荡
 */
public class OscillationEvaluator implements TypeEvaluator {
    private View view;
    public OscillationEvaluator(View view){
        this.view = view;
    }

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());//x坐标线性变化
        float y = 120 * (float) (Math.sin(0.01 * Math.PI * x)) + view.getHeight() / 2;//y坐标取相对应函数值
        return new Point(x, y);
    }
}
