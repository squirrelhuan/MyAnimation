package com.example.huan.myanimation.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.huan.myanimation.view.AnimationViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机水平线
 * Created by huan on 2017/10/20.
 */

public class StochasticHorizontalLine extends Animator {


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

    public StochasticHorizontalLine(int partCount) {
        this.partCount = partCount;
    }

    private int scaleY = 5;
    private int maxY = 50;
    private ArrayList<Integer> nums = new ArrayList<Integer>();//百位级代表x坐标，余数代表宽度

    @Override
    public void init(int w, int h) {
        super.init(w, h);
        setPartCount(partCount);
        mCanvas.setBitmap(((AnimationViewGroup) targetView).mBitmap);
        Random random = new Random();
        nums.clear();
        while (true) {
            int c = 0;
            if (nums != null && nums.size() > 0) {
                for (int i = 0; i < nums.size(); i++) {
                    c += nums.get(i) / 100;
                }
            }
            int a = (1 + random.nextInt(9)) * scaleY * 100;//maxY = 8*scaleY+8
            int b = a / 5 + (int) Math.random() * ((a / 2 - a / 5 + 1));
            nums.add(a + b);
            if (c >= h) {
                break;
            }
        }
        Log.i("cgq", "h=" + h + ",nums.size = " + nums.size());
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
        canvas.drawBitmap(targetView.mBitmap, 0, 0, new Paint());

        Paint paint = new Paint();
        int y = 0;
        for (int i = 0; i < nums.size(); i++) {
            y += nums.get(i) / 100;
            if (nums.size() > i + 1) {
                float l = (1 - mFactor) * maxY*1.1f - (nums.get(i) % 100);
                if (mFactor != 1&&mFactor!=0) {
                    paint.setStrokeWidth(l);
                    canvas.drawLine(0, y+l/2, width, y+l/2, paint);
                }
                Log.i("cgq", "i=" + i + ",nums.size = " + nums.size());
            }
        }
    }

}
