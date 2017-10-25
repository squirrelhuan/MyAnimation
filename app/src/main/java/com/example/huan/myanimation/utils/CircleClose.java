package com.example.huan.myanimation.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;

import com.example.huan.myanimation.view.AnimationViewGroup;

/**
 * 盒状收缩
 * Created by huan on 2017/10/20.
 */

public class CircleClose extends Animator {


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

    public CircleClose(int partCount) {
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
        Paint paint = new Paint();

        Path path = new Path();
        double raduis =Math.sqrt(Math.pow(height/2,2)+Math.pow(width/2,2));
        //按照逆时针方向添加一个圆
        path.addCircle(width/2, height/2, (float)(raduis*(1-mFactor)), Path.Direction.CCW);
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
