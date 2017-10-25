package com.example.huan.myanimation.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;

import com.example.huan.myanimation.view.AnimationViewGroup;

/**
 * 纵向棋盘式
 * Created by huan on 2017/10/20.
 */

public class VerticleChessboard extends Animator {


    private int partCount = 6;

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        cell_width = partCount != 0 ? width / partCount : 1;
        cell_height = partCount != 0 ? height / partCount : 1;
        Log.i("cgq", "width=" + width + ",cell_width=" + cell_width);
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
    private float cell_height;
    private Matrix[] mMatrices = new Matrix[partCount];

    public VerticleChessboard(int partCount) {
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
            src[2] = (i + 1) * cell_width;
            src[3] = 0;
            src[4] = src[2];
            src[5] = height;
            src[6] = src[0];
            src[7] = src[5];

            //Log.i("cgq","cell_width="+cell_width);

            dst = src.clone();
            dst[2] = src[2] - (1 - mFactor) * cell_width;
            Log.i("cgq", "mFactor=" + mFactor);
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
        (targetView).dispatchDrawH(mCanvas);
        canvas.drawBitmap(targetView.mBitmap, 0, 0, new Paint());
        for (int i = 0; i < partCount; i++) {
            for (int j = 0; j < partCount; j++) {
                if (j % 2 == 0) {
                    canvas.drawRect(cell_width * (j), cell_height * (i+mFactor), cell_width * (j+1f), cell_height * (i+1), new Paint());
                } else {
                    canvas.drawRect(cell_width * (j), cell_height * (i+mFactor*1.5f), cell_width * (j+1), cell_height * (i+1.5f), new Paint());
                }
            }
        }
    }

}
