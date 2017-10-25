package com.example.huan.myanimation.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

import com.example.huan.myanimation.view.AnimationViewGroup;

/**
 * 新闻快报
 * Created by huan on 2017/10/20.
 */

public class ActionNews extends Animator {


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

    public ActionNews(int partCount) {
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
        //canvas.clipRect(width * mFactor, height * mFactor, width * (1 - mFactor), height * (1 - mFactor));
        canvas.translate(width/2*(1-mFactor),height/2*(1-mFactor));
        canvas.scale(mFactor,mFactor);
        canvas.rotate(-360*mFactor,width/2,height/2);
        canvas.drawBitmap(targetView.mBitmap, 0, 0, new Paint());

    }

}
