package com.example.huan.myanimation.view;

import android.animation.ValueAnimator;
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
import android.widget.RelativeLayout;

import com.example.huan.myanimation.utils.AnimationUtil;
import com.example.huan.myanimation.utils.Animator;

import java.util.Map;

/**
 * Created by huan on 2017/10/20.
 */

public class AnimationViewGroup extends RelativeLayout {

    /**
     * 图片的折叠后的总宽度
     */
    private float mTranslateDis;

    private int mNumOfFolds = 8;

    private Matrix[] mMatrices = new Matrix[mNumOfFolds];


    private Paint mShadowPaint;
    private LinearGradient mShadowGradientShader;

    public AnimationViewGroup(Context context) {
        this(context, null);
    }

    public AnimationViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        for (int i = 0; i < mNumOfFolds; i++) {
            mMatrices[i] = new Matrix();
        }

        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0, Color.BLACK,
                Color.TRANSPARENT, Shader.TileMode.CLAMP);
        mShadowPaint.setShader(mShadowGradientShader);
        this.setWillNotDraw(false);
        //this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(child.getMeasuredWidth(),
                child.getMeasuredHeight());
    }

    public int width;
    public int height;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());

        width = getMeasuredWidth();
        height = getMeasuredHeight();

        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);

        if (animator != null) {
            animator.init(width, height);
            animator.setdata();
        }
    }

    public void dispatchDrawH(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    public float mFactor = 0.6f;

    private Canvas mCanvas = new Canvas();
    public Bitmap mBitmap;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (animator != null) {
            animator.reDraw(canvas);
        } else {
            super.dispatchDraw(canvas);
        }
    }

    private Animator animator;

    /**
     * 执行动画
     *
     * @param animationType
     */
    public void startAnimation(int animationType) {
        //if(animator==null)return;
        animator = AnimationUtil.getAnimatorByType(animationType, width, height);
        animator.setTargetView(this);
        animator.start();
    }

    public void canelAnimation() {
        animator.valueAnimator.cancel();
    }
}
