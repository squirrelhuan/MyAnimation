package com.example.huan.myanimation.utils;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.huan.myanimation.view.AnimationViewGroup;

/**
 * 弹跳小球
 * Created by huan on 2017/10/20.
 */

public class BounceAway extends Animator {


    private int partCount = 6;

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        cell_width = partCount != 0 ? width / partCount : 1;
        Log.i("cgq", "width=" + width + ",cell_width=" + cell_width);
        this.partCount = partCount;
    }

    private float cell_width;

    public BounceAway(int partCount) {
        this.partCount = partCount;
    }

    @Override
    public void init(int w, int h) {
        super.init(w, h);
        setPartCount(partCount);
        mCanvas.setBitmap(((AnimationViewGroup) targetView).mBitmap);
    }

    @Override
    public void setTargetView(AnimationViewGroup targetView) {
        super.setTargetView(targetView);
        setPartCount(this.partCount);
    }

    private ValueAnimator scaleAnimation;
    private float scale_start = 0.2f;//初始值
    private int indexOfAnimation = 0;//当前动画

    @Override
    public void start() {
        scaleAnimation = valueAnimator.ofFloat(scale_start, 1);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        scaleAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFactor = (float) valueAnimator.getAnimatedValue();
                setdata();
                indexOfAnimation = 1;//切换成第二个动画
                ((View) targetView).invalidate();
            }
        });
        scaleAnimation.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationEnd(android.animation.Animator animator) {
                indexOfAnimation = 0;//切换成第1个动画
                valueAnimator.start();
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationRepeat(android.animation.Animator animator) {

            }
        });
        valueAnimator = ValueAnimator.ofFloat(0, 1, 0, 1, 0);
       // valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFactor = (float) valueAnimator.getAnimatedValue();
                setdata();
                ((View) targetView).invalidate();
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(0);
        valueAnimator.setStartDelay(0000);
        animatorListener = new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animator) {
                indexOfAnimation = 0;
            }

            @Override
            public void onAnimationEnd(android.animation.Animator animator) {
                scaleAnimation.start();
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationRepeat(android.animation.Animator animator) {

            }
        };
        if (animatorListener != null) {
            valueAnimator.addListener(animatorListener);
        }
        valueAnimator.start();
    }

    @Override
    public void reData(float value) {


    }

    private Canvas mCanvas = new Canvas();

    @Override
    public void reDraw(Canvas canvas) {

        switch (indexOfAnimation) {
            case 0://第一个动画
                (targetView).dispatchDrawH(mCanvas);
                Paint paint = new Paint();

                Path path = new Path();
                double raduis = Math.sqrt(Math.pow(height / 2, 2) + Math.pow(width / 2, 2));
                //按照逆时针方向添加一个圆
                path.addCircle(width / 2, height / 2 - (float) (raduis * scale_start) + height / 2 * mFactor, (float) (raduis * scale_start), Path.Direction.CCW);
                //先将canvas保存
                canvas.save();
                //设置为在圆形区域内绘制
                canvas.clipPath(path);
                canvas.translate(0, (float) (-(float) (raduis * scale_start) + (height / 2) * mFactor));
                //绘制Bitmap
                canvas.drawBitmap(targetView.mBitmap, 0, 0, paint);
                //恢复Canvas
                canvas.restore();
                break;
            case 1://第二个动画
                reDraw2(canvas);
                break;
        }
    }

    public void reDraw2(Canvas canvas) {
        (targetView).dispatchDrawH(mCanvas);
        Paint paint = new Paint();

        Path path = new Path();
        double raduis =Math.sqrt(Math.pow(height/2,2)+Math.pow(width/2,2));
        //按照逆时针方向添加一个圆
        path.addCircle(width/2, height/2, (float)(raduis*(mFactor+scale_start)), Path.Direction.CCW);
        //先将canvas保存
        canvas.save();
        //设置为在圆形区域内绘制
        canvas.clipPath(path);
        //绘制Bitmap
        canvas.drawBitmap(targetView.mBitmap, 0, 0, paint);
        //恢复Canvas
        canvas.restore();
    }

}
