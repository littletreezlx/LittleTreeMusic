package com.example.littletreemusic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 春风亭lx小树 on 2017/11/25.
 */

public class ViewPager_view0 extends View {

    Paint mPaint;
    private int mRectCount;
    private float mRectWidth;
    private float mRectHeight;
    private float mOffset=20;

    //View的宽度
    private int mViewWidth;
    //产生渐变效果
    private LinearGradient mLinearGradient;
    //每个小矩形的当前高度
    private float mCurrentHeight;

    //渐变色顶部颜色
    private int mTopColor= Color.YELLOW;
    //渐变色底部颜色
    private int mBottomColor=Color.BLUE;
    //View重绘延时时间
    private int mDelayTime;

    private byte[] mBytes;
    private float[] mPoints;
    private Rect mRect = new Rect();


    public ViewPager_view0(Context context){
        super(context);
        init();
//        setWillNotDraw(false);
    }

    public ViewPager_view0(Context context,AttributeSet attrs){
        super(context, attrs);
        init();
//        setWillNotDraw(false);
    }

    private void init() {

        mPaint = new Paint ();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.FILL);

        setBackgroundColor(Color.parseColor("#ffffff"));

    }

    public void updateVisualizer(byte[] bytes) {
        mBytes = bytes;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {

//        DisplayMetrics dm =getResources().getDisplayMetrics();
//        int screen_w = dm.widthPixels;
//        int screen_h = dm.heightPixels;
        setBackgroundColor(Color.parseColor("#ffffff"));
        if (mBytes != null){
            if (mPoints == null) {
                mPoints = new float[mBytes.length];
            }

            for (int i = 0; i < 32; i++) {
                if (mBytes[i] < 0) {
                    mBytes[i] = 127;
                }
                mPoints[i * 4] = mRect.width() * i / 32;
                mPoints[i * 4 + 1] = mRect.height() / 2;
                mPoints[i * 4 + 2] = mRect.width() * i / 32;
                mPoints[i * 4 + 3] = 2 + mRect.height() / 2 + mBytes[i];
            }
            canvas.drawLines(mPoints, mPaint);
        }
        postInvalidateDelayed(100);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = getMeasuredWidth();
        mRectHeight = getMeasuredHeight();
//        mRectWidth = (int) (mViewWidth * 0.6 / mRectCount);
        mRectWidth=(mViewWidth-9*mOffset)/10;
        mLinearGradient = new LinearGradient(0, 0, mRectWidth, mRectHeight,
                mTopColor, mBottomColor, Shader.TileMode.CLAMP);
        //给画笔设置Shader
        mPaint.setShader(mLinearGradient);
    }


}
