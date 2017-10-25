package com.example.huan.myanimation.utils;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.huan.myanimation.view.AnimationViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 卷轴效果
 * Created by huan on 2017/10/20.
 */

public class Reel extends Animator {


    private int partCount = 6;

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        cell_width = partCount != 0 ? width / partCount : 1;
        Random random = new Random();
        Log.i("cgq", "cell_width=" + cell_width);
        //初始化数据
        pointFs.clear();
        for (int i = 0; i < partCount; i++) {
            if (i == 0) {
                pointFs.add(new PointF(width / 2, 0));
            } else {
                PointF pointF = pointFs.get(i - 1);
                int a = height / partCount;
                Log.i("cgq", "a=" + a);
                float b = a / 3 + random.nextInt(a * 2 / 3);
                float c = random.nextInt(100) + width / 2;
                PointF pointFc = new PointF(c, pointF.y + b);
                pointFs.add(pointFc);
            }
        }

        float h2 = pointFs.get(partCount - 1).y;
        if (h2 < height) {
            for (int j = 0; j < partCount; j++) {
                PointF pointF = pointFs.get(0);
                pointF.y = pointF.y * (height / h2);
                pointFs.add(pointF);
                pointFs.remove(0);
            }
        }
        Log.i("cgq", "width=" + width + ",cell_width=" + cell_width);
        this.partCount = partCount;
    }

    private float cell_width;

    public Reel(int partCount) {
        this.partCount = partCount;
    }

    @Override
    public void init(int w, int h) {
        super.init(w, h);
        setPartCount(partCount);
        mCanvas.setBitmap(((AnimationViewGroup) targetView).mBitmap);
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
                //((View) targetView).invalidate();
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
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
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

    /**
     * 用作刷新数据
     *
     * @param value
     */
    @Override
    public void reData(float value) {


    }

    private Canvas mCanvas = new Canvas();

    private List<PointF> pointFs = new ArrayList<>();


    Bitmap new2 = null;
    @Override
    public void reDraw(Canvas canvas) {
       if(new2==null){
           Matrix m = new Matrix();
           m.postScale(1, -1);   //镜像垂直翻转
           new2 = Bitmap.createBitmap(targetView.mBitmap, 0, 0, width, height, m, true);
       }
        switch (indexOfAnimation) {
            case 0://第一个动画
                (targetView).dispatchDrawH(mCanvas);
                Paint paint = new Paint();

                //Log.i("cgq", "path=" + path.g);
                //先将canvas保存
                canvas.save();
                //设置为在圆形区域内绘制
                canvas.clipRect(width/20,0,width*19/20,height*(1-mFactor));
                //绘制Bitmap
                canvas.drawBitmap(targetView.mBitmap, 0, 0, paint);

                //恢复Canvas
                canvas.restore();

                //下部翻转
                //先将canvas保存
                canvas.save();

                Matrix m = new Matrix();
                m.postScale(1, -1);   //镜像垂直翻转
                new2 = Bitmap.createBitmap(targetView.mBitmap, 0, 0, width, height, m, true);
                float h1 = height*(1-mFactor);//轴高
                float h2 = h1 + height/30+height/50*mFactor;//轴高
                canvas.clipRect(0,h1,width,h2);
                canvas.drawColor(Color.WHITE);
                paint.setAlpha((int)(255*0.2));
                canvas.drawBitmap(new2,0,height-2*height*mFactor+( height/30+height/50*mFactor),paint);
                //canvas.translate(0,height - height*(1-mFactor));
                //恢复Canvas
                canvas.restore();

                break;
            case 1://第二个动画
                // reDraw2(canvas);
                break;
        }
    }

    public void reDraw2(Canvas canvas) {
        (targetView).dispatchDrawH(mCanvas);
        Paint paint = new Paint();

        Path path = new Path();
        double raduis = Math.sqrt(Math.pow(height / 2, 2) + Math.pow(width / 2, 2));
        //按照逆时针方向添加一个圆
        path.addCircle(width / 2, height / 2, (float) (raduis * (mFactor + scale_start)), Path.Direction.CCW);
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
