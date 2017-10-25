package com.example.huan.myanimation.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.Log;

import com.example.huan.myanimation.view.AnimationViewGroup;

/**
 * 盒状收缩
 * Created by huan on 2017/10/20.
 */

public class BoxOpen extends Animator {


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

    public BoxOpen(int partCount) {
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

    @Override
    public void reDraw(Canvas canvas) {

        (targetView).dispatchDrawH(mCanvas);
        canvas.clipRect(width * mFactor, height * mFactor, width * (1 - mFactor), height * (1 - mFactor));
        canvas.drawBitmap(targetView.mBitmap, 0, 0, new Paint());
        canvas.save();
        //canvas.translate( cell_width * i,0);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        //canvas.drawRect(0, 0, width, height, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        //canvas.drawRect(width * mFactor, height * mFactor, width * (1 - mFactor), height * (1 - mFactor), paint);

        canvas.restore();
    }

}
