package com.example.huan.myanimation.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.example.huan.myanimation.view.AnimationViewGroup;

/**
 * 阶梯状左下方展开
 * Created by huan on 2017/10/20.
 */

public class LadderRightDown extends Animator {


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

    public LadderRightDown(int partCount) {
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
        //path.addCircle(width/2, height/2, (float)(raduis*mFactor), Path.Direction.CCW);
        int x = (int)(width*1.5*(1-mFactor));
        int y = (int) ((height*1.5)*(1-mFactor));
        path.moveTo((float) (width*1.5),(float)(height*1.5));
        path.moveTo(x-(partCount)*cell_width,(float)(height*1.5));
        for (int i=0;i<partCount;i++){
            path.lineTo(x-(partCount-i)*cell_width,y-(i)*height/partCount);//左上
            path.lineTo(x-(partCount-i)*cell_width,y-(i+1)*height/partCount);//左上
        }
        path.lineTo((float) (width*1.5),y-(partCount+1)*height/partCount);//左上
        path.lineTo((float) (width*1.5),(float)(height*1.5));
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
