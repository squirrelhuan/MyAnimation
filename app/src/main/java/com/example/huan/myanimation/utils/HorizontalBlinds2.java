package com.example.huan.myanimation.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;

import com.example.huan.myanimation.view.AnimationViewGroup;

/**水平百叶窗
 * Created by huan on 2017/10/20.
 */

public  class HorizontalBlinds2 extends Animator{


    private int partCount = 6;

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        cell_width = partCount!=0?width/partCount:1;
        Log.i("cgq","width="+width+",cell_width="+cell_width);
        mMatrices = new Matrix[partCount];
        for (int i = 0; i < partCount; i++) {
            mMatrices[i] = new Matrix();
        }
        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0, Color.BLACK,
                Color.TRANSPARENT, Shader.TileMode.CLAMP);
        mShadowPaint.setShader(mShadowGradientShader);
        this.partCount = partCount;
    }

    private float cell_width;
    private Matrix[] mMatrices = new Matrix[partCount];

    public HorizontalBlinds2(int partCount) {
        this.partCount = partCount;
    }

    @Override
    public void init(int w, int h) {
        super.init(w, h);
        setPartCount(partCount);
        mCanvas.setBitmap(((AnimationViewGroup)targetView).mBitmap);
    }

    @Override
    public void setTargetView(AnimationViewGroup targetView) {
        super.setTargetView(targetView);
        setPartCount(this.partCount );
    }

    private Paint mShadowPaint;
    private LinearGradient mShadowGradientShader;
    @Override
    public void reData(float value) {

        float[] src = new float[partCount];
        float[] dst = new float[partCount];

        for (int i = 0; i < partCount; i++) {
            mMatrices[i].reset();
            src[0] = i * cell_width;
            src[1] = 0;
            src[2] = (i+1)* cell_width;
            src[3] = 0;
            src[4] = src[2];
            src[5] = height;
            src[6] = src[0];
            src[7] = src[5];

            //Log.i("cgq","cell_width="+cell_width);

            dst = src.clone();
            dst[2] = src[2]-(1-mFactor)* cell_width;
            Log.i("cgq","mFactor="+mFactor);
            dst[4] = dst[2];

            for (int y = 0; y < 8; y++) {
                dst[y] = Math.round(dst[y]);
            }
            mMatrices[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        }
    }

    private boolean isReady;
    private Canvas mCanvas = new Canvas();
    @Override
    public void reDraw(Canvas canvas) {
        for (int i = 0; i < partCount; i++) {
            //canvas = new Canvas();
            canvas.save();

            canvas.concat(mMatrices[i]);
            canvas.clipRect(0, cell_width * i, width, cell_width * (i + 1));
            //canvas.drawColor(Color.RED);
            if (isReady) {
                canvas.drawBitmap(targetView.mBitmap, 0, 0, null);
            } else {
                // super.dispatchDraw(canvas);
                (targetView).dispatchDrawH(mCanvas);
                canvas.drawBitmap(targetView.mBitmap, 0, 0, null);
                isReady = true;
            }
            canvas.translate(0, cell_width * i);
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
