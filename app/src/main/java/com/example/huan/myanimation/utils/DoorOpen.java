package com.example.huan.myanimation.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.Log;

import com.example.huan.myanimation.view.AnimationViewGroup;

/**
 * 十字交叉展开
 * Created by huan on 2017/10/20.
 */

public class DoorOpen extends Animator {


    private int partCount = 6;

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        mMatrices = new Matrix[partCount];
        for (int i = 0; i < partCount; i++) {
            mMatrices[i] = new Matrix();
        }
        cell_width = partCount != 0 ? width / partCount : 1;
        Log.i("cgq", "width=" + width + ",cell_width=" + cell_width);
        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0, Color.BLACK,
                Color.TRANSPARENT, Shader.TileMode.CLAMP);
        mShadowPaint.setShader(mShadowGradientShader);
        this.partCount = partCount;
    }

    private float cell_width;

    public DoorOpen(int partCount) {
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

    private Matrix[] mMatrices = new Matrix[partCount];
    @Override
    public void reData(float value) {
        float[] src = new float[8];
        float[] dst = new float[8];

        for (int i = 0; i < partCount; i++) {
            mMatrices[i].reset();

                src[0] = i * cell_width;
                src[1] = 0;
                src[2] = (i+1)* cell_width;
                src[3] = src[1];
                src[4] = src[2];
                src[5] = height;
                src[6] = src[0];
                src[7] = src[5];


            //Log.i("cgq","cell_width="+cell_width);

            dst = src.clone();
            if(i%2==0) {
                dst[2] = src[2]- (mFactor* cell_width);
                dst[3] = src[3] + (mFactor) * cell_width/2;
                dst[4] = dst[2];
                dst[5] = src[5]- (mFactor) * cell_width/2;
            }else {
                dst[0] = src[0] + mFactor * cell_width;
                dst[1] = src[1] + mFactor * cell_width/2;
                dst[6] = dst[0];
                dst[7] = src[7] - mFactor*cell_width/2;
            }
            Log.i("cgq", "mFactor=" + mFactor);
           for (int y = 0; y < 8; y++) {
                dst[y] = Math.round(dst[y]);
            }
            mMatrices[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        }

    }

    private Canvas mCanvas = new Canvas();
    private Paint mShadowPaint;
    private LinearGradient mShadowGradientShader;
    private boolean isReady;
    @Override
    public void reDraw(Canvas canvas) {
        for (int i = 0; i < partCount; i++) {
            //canvas = new Canvas();
            canvas.save();

            canvas.concat(mMatrices[i]);
            canvas.clipRect( cell_width * i,0, cell_width * (i + 1),height);
            //canvas.drawColor(Color.RED);
            if (isReady) {
                canvas.drawBitmap(targetView.mBitmap, 0, 0, null);
            } else {
                // super.dispatchDraw(canvas);
                (targetView).dispatchDrawH(mCanvas);
                canvas.drawBitmap(targetView.mBitmap, 0, 0, null);
                isReady = true;
            }
            canvas.translate( cell_width * i,0);
            if (i % 2 == 0) {
                //canvas.drawRect(0, 0, getWidth(), mFlodWidth, mSolidPaint);
                canvas.drawRect(0, 0,width, cell_width, mShadowPaint);
            } else {
                // canvas.drawRect(0, 0, getWidth(), mFlodWidth, mSolidPaint);
                canvas.drawRect(0, 0, width, cell_width, mShadowPaint);
            }
            canvas.restore();
        }
    }

}
