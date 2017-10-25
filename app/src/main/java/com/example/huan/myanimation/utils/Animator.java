package com.example.huan.myanimation.utils;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

import com.example.huan.myanimation.view.AnimationViewGroup;

import java.util.Map;

/**
 * Created by huan on 2017/10/20.
 */

public abstract class Animator{

    public AnimationViewGroup targetView;
    private int duration = 2000;
    private AnimatorListener listener;
    public AnimatorListener getListener() {
        return listener;
    }

    public void setListener(AnimatorListener listener) {
        this.listener = listener;
    }
    public Animator() {
        valueAnimator = ValueAnimator.ofFloat(0,1);
       // init();
    }
    public float mFactor;
    public abstract void reData(float value);
    public abstract void reDraw(Canvas canvas);
    public int width;
    public int height;
    public void init(int w,int h){
        width = w;
        height = h;
    }
    public void setdata(){
        reData(mFactor);
    }

    public android.animation.Animator.AnimatorListener animatorListener;
    public ValueAnimator valueAnimator;
    public void  start(){

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFactor = (float)valueAnimator.getAnimatedValue();
                setdata();
                ((View)targetView).invalidate();
            }
        });
        valueAnimator.setDuration(getDuration());
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setStartDelay(0000);
        if(animatorListener!=null){
        valueAnimator.addListener(animatorListener);}
        valueAnimator.start();
    }


    public interface AnimatorListener{
        void reData(Map<String,Object> map);
        void reDraw(Map<String,Object> map);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTargetView(AnimationViewGroup targetView) {
        this.targetView = targetView;
    }

}
