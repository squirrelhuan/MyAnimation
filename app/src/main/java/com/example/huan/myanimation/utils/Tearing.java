package com.example.huan.myanimation.utils;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.huan.myanimation.view.AnimationViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 撕裂效果
 * Created by huan on 2017/10/20.
 */

public class Tearing extends Animator {


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

    public Tearing(int partCount) {
        this.partCount = partCount;
    }

    @Override
    public void init(int w, int h) {
        super.init(w, h);
        setPartCount(partCount);
        mCanvas.setBitmap(((AnimationViewGroup) targetView).mBitmap);
    }

    private ValueAnimator scaleAnimation;
    private float scale_start = 0;//初始值
    private int indexOfAnimation = 0;//当前动画

    @Override
    public void start() {
        scaleAnimation = valueAnimator.ofFloat(scale_start, 1);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setRepeatCount(0);
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
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        // valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFactor = (float) valueAnimator.getAnimatedValue();
                setdata();
                ((View) targetView).invalidate();
            }
        });
        valueAnimator.setDuration(2000);
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
    private int strokeWidth =3;

    @Override
    public void reDraw(Canvas canvas) {

        switch (indexOfAnimation) {
            case 0://第一个动画
                (targetView).dispatchDrawH(mCanvas);
                Paint paint = new Paint();
                Path path = new Path();
                int x0 = (int) (width / 2);
                int y0 = (int) (0);
                path.moveTo((float) x0, (float) y0);
                for (int i = 0; i < partCount*mFactor; i++) {
                    path.lineTo(pointFs.get(i).x, pointFs.get(i).y);//左上
                }
                for (int i = 1; i <=partCount*mFactor; i++) {
                    path.lineTo(pointFs.get((int)(partCount*mFactor)  - i).x + strokeWidth, pointFs.get((int)(partCount*mFactor)  - i).y);//左上
                }
                path.lineTo(x0, y0);
                //Log.i("cgq", "path=" + path.g);
                //先将canvas保存
                canvas.save();
                //设置为在圆形区域内绘制
               // canvas.clipPath(path_left);
                //canvas.translate( (float)-(width * mFactor),0);
                //绘制Bitmap
                canvas.drawBitmap(targetView.mBitmap, 0, 0, paint);

                Paint paint1 =new Paint();
                paint1.setColor(Color.WHITE);
                canvas.drawPath(path, paint1);

                //canvas.clipPath(width);
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
        Path path_left = new Path();
        Path path_right = new Path();
        int x0 = (int) (width / 2);
        int y0 = (int) (0);
        path.moveTo((float) x0, (float) y0);
        path_left.moveTo(0,0);
        path_right.moveTo(width,height);
        for (int i = 0; i < partCount; i++) {
            path.lineTo(pointFs.get(i).x, pointFs.get(i).y);//左上
            path_left.lineTo(pointFs.get(i).x-(width * mFactor), pointFs.get(i).y);
        }
        for (int i = 0; i < partCount; i++) {
            path.lineTo(pointFs.get(partCount - 1 - i).x + strokeWidth, pointFs.get(partCount - 1 - i).y);//左上
            path_right.lineTo(pointFs.get(partCount - 1 - i).x + strokeWidth+(width * mFactor), pointFs.get(partCount - 1 - i).y);//左上
        }
        path.lineTo(x0, y0);
        path_left.lineTo(0,height);
        path_left.lineTo(0,0);
        path_right.lineTo(width,0);
        path_right.lineTo(width,height);
        //Log.i("cgq", "path=" + path.g);
        //先将canvas保存
        canvas.save();
        //设置为在圆形区域内绘制
        canvas.clipPath(path_left);
        canvas.translate( (float)-(width * mFactor),0);
        //绘制Bitmap
        canvas.drawBitmap(targetView.mBitmap, 0, 0, paint);
        //canvas.drawLine(0, 0, width, height, new Paint());
        // Paint paint1 = new Paint();
        //paint1.setStrokeWidth(10);
        //paint1.setColor(Color.CYAN);
        //canvas.drawPath(path, paint1);
        //恢复Canvas
        canvas.restore();

        //先将canvas保存
        canvas.save();
        canvas.clipPath(path_right);
        canvas.translate( (float)(width * mFactor),0);
        //绘制Bitmap
        canvas.drawBitmap(targetView.mBitmap, 0, 0, paint);
    }

}
