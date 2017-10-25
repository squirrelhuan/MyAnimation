package com.example.huan.myanimation.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by huan on 2017/10/17.
 */

public class FoldLayout extends ViewGroup {

    private static final int NUM_OF_POINT = 8;
    /**
     * 图片的折叠后的总宽度
     */
    private float mTranslateDis;

    protected float mFactor = 1f;

    private int mNumOfFolds = 8;

    private Matrix[] mMatrices = new Matrix[mNumOfFolds];

    private Paint mSolidPaint;

    private Paint mShadowPaint;
    private Matrix mShadowGradientMatrix;
    private LinearGradient mShadowGradientShader;

    private float mFlodWidth;
    private float mTranslateDisPerFlod;

    public FoldLayout(Context context) {
        this(context, null);
    }

    public FoldLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        for (int i = 0; i < mNumOfFolds; i++) {
            mMatrices[i] = new Matrix();
        }

        mSolidPaint = new Paint();
        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0, Color.BLACK,
                Color.TRANSPARENT, Shader.TileMode.CLAMP);
        mShadowPaint.setShader(mShadowGradientShader);
        mShadowGradientMatrix = new Matrix();
        this.setWillNotDraw(false);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(child.getMeasuredWidth(),
                child.getMeasuredHeight());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());

        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        updateFold();

    }

    private int height0;
    private int topY;
    private void updateFold() {
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        mTranslateDis = h * mFactor;
        mFlodWidth = h / mNumOfFolds;
        mTranslateDisPerFlod = mTranslateDis / mNumOfFolds;

        int alpha = (int) (255 * (1 - mFactor));
        mSolidPaint.setColor(Color.argb((int) (alpha * 0.8F), 0, 0, 0));

        mShadowGradientMatrix.setScale(1, mFlodWidth);
        mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
        mShadowPaint.setAlpha(alpha);

        float depth = (float) (Math.sqrt(mFlodWidth * mFlodWidth
                - mTranslateDisPerFlod * mTranslateDisPerFlod) / 2);

        float[] src = new float[NUM_OF_POINT];
        float[] dst = new float[NUM_OF_POINT];

        for (int i = 0; i < mNumOfFolds; i++) {
            mMatrices[i].reset();
            src[0] = 0;
            src[1] = i * mFlodWidth;
            src[2] = w;
            src[3] = src[1];
            src[4] = src[2];
            src[5] = (i + 1) * mFlodWidth;
            src[6] = src[0];
            src[7] = src[5];

            boolean isEven = i % 2 == 0;

            dst[0] = isEven ? depth : 0;
            dst[1] = topY+i * mTranslateDisPerFlod;
            dst[2] = isEven ? w - depth : w;
            dst[3] = dst[1];
            dst[4] = isEven ? w : w - depth;
            dst[5] = dst[1] + mTranslateDisPerFlod;
            dst[6] = isEven ? 0 : depth;
            dst[7] = dst[5];

            for (int y = 0; y < 8; y++) {
                dst[y] = Math.round(dst[y]);
            }

            mMatrices[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        }
    }

    private Canvas mCanvas = new Canvas();
    private Bitmap mBitmap;
    private boolean isReady;

    private boolean isFirst = true;
    @Override
    protected void dispatchDraw(Canvas canvas) {

        /*if(getChildAt(0)!=null&&getChildAt(0).getVisibility()==GONE){
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.jps_bule_01));
            canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/5,paint);
            if(isFirst){}
            return;
        }*/
        if (mFactor == 0)
            return;
        if (mFactor == 1) {
            super.dispatchDraw(canvas);
            return;
        }
        for (int i = 0; i < mNumOfFolds; i++) {
            canvas.save();

            canvas.concat(mMatrices[i]);
            canvas.clipRect(0, mFlodWidth * i, getWidth(), mFlodWidth * (i + 1));
            //canvas.drawColor(Color.RED);
            if (isReady) {
                canvas.drawBitmap(mBitmap, 0, 0, null);
            } else {
                // super.dispatchDraw(canvas);
                super.dispatchDraw(mCanvas);
                canvas.drawBitmap(mBitmap, 0, 0, null);
                isReady = true;
            }
            canvas.translate(0, mFlodWidth * i);
            if (i % 2 == 0) {
                canvas.drawRect(0, 0, getWidth(), mFlodWidth, mSolidPaint);
            } else {
                canvas.drawRect(0, 0, getWidth(), mFlodWidth, mShadowPaint);
            }
            canvas.restore();
        }
    }
    //...dispatchDraw
    public void setFactor(float factor) {
        if (height0 == 0) {
            height0 = getChildAt(0).getHeight();
        }
        if(factor>1)factor=1;
        this.mFactor = factor;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        //layoutParams.setMargins(0,(int)( height0*(1-factor)),0,0);
        //layoutParams.height = (int)(height0*(factor));
        //this.setPadding(0,(int)( height0*(1-factor)),0,0);
        //setLayoutParams(layoutParams);
        topY = (int)(height0*(1-factor));
        updateFold();
        invalidate();

    }

    public float getFactor() {
        return mFactor;
    }

    public int getHeight0() {
        return height0;
    }

    @Override
    public void addView(View child) {
        //child.setVisibility(GONE);
        super.addView(child);
    }
}
