package com.example.huan.myanimation.utils;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

import com.example.huan.myanimation.view.AnimationViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 溶解
 * Created by huan on 2017/10/20.
 */

public class Dissolution extends Animator {


    private int partCount = 6;

    public int getPartCount() {
        return partCount;
    }

    private List<Integer> points = new ArrayList<>();

    public void setPartCount(final int partCount) {
        cell_width = partCount != 0 ? width / partCount : 1;
        Log.i("cgq", "width=" + width + ",cell_width=" + cell_width);
        this.partCount = partCount;
        points.clear();
        points_c.clear();
        for (int i = 0; i < partCount; i++) {
            for (int j = 0; j < Math.ceil(height / cell_width); j++) {
                points.add(0);//i * partCount + j,
                points_c.add(j*partCount+i);
            }
        }
        animatorListener = new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationEnd(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationCancel(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationRepeat(android.animation.Animator animator) {
                points.clear();
                points_c.clear();
                for (int i = 0; i < partCount; i++) {
                    for (int j = 0; j < Math.ceil(height / cell_width); j++) {
                        points.add(0);
                        points_c.add(j*partCount+i);
                    }
                }
            }
        };
    }

    private float cell_width;

    public Dissolution(int partCount) {
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

    @Override
    public void reData(float value) {


    }

    private Canvas mCanvas = new Canvas();
    private List<Integer> points_c = new ArrayList<>();

    @Override
    public void reDraw(Canvas canvas) {

        (targetView).dispatchDrawH(mCanvas);
        // canvas.clipRect(width * mFactor, height * mFactor, width * (1 - mFactor), height * (1 - mFactor));
        canvas.drawBitmap(targetView.mBitmap, 0, 0, new Paint());
        canvas.save();
        //canvas.translate( cell_width * i,0);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        //canvas.drawRect(0, 0, width, height, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        int k = (int) (partCount * (2 - mFactor));

        for (int p = 0; p < k; p++) {
            Random random = new Random();
            int n = points_c.size() == 0 ? 0 : random.nextInt(points_c.size());
            if(points_c.size()>0){
            points.set(points_c.get(n), 1);
            points_c.remove(n);}
        }

        for (int i = 0; i <partCount; i++) {
            for (int j = 0; j < Math.ceil(height / cell_width); j++) {
                Log.i("cgq","index =" +j* partCount +i);
                if (points.get(j* partCount +i) == 0) {
                    canvas.drawRect(i * cell_width, j * cell_width, (i + 1) * cell_width, (j + 1) * cell_width, paint);
                }
            }
        }

        canvas.restore();
    }

}
