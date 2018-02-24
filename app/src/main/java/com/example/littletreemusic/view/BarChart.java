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

public class BarChart extends View {

    Paint mPaint;
    private int mRectCount;
    private float mRectWidth;
    private float mRectHeight;
    private float mOffset = 20;
    //View的宽度
    private int mViewWidth;
    //产生渐变效果
    private LinearGradient mLinearGradient;
    //每个小矩形的当前高度
    private float mCurrentHeight;

    //渐变色顶部颜色
    private int mTopColor = Color.RED;
    private int mMiddleColor = Color.YELLOW;
    //渐变色底部颜色
    private int mBottomColor = Color.GREEN;
    //View重绘延时时间
    private int mDelayTime;

    private byte[] mBytes;
    private float[] mPoints;
    private Rect mRect = new Rect();
    int w, h;
    float rw,rh;


    public BarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        setBackgroundColor(Color.parseColor("#ffffff"));
        init();
        setWillNotDraw(false);
    }

    public BarChart(Context context) {
        super(context);
//        setBackgroundColor(Color.parseColor("#ffffff"));
        init();
        setWillNotDraw(false);
    }

    public BarChart(Context context, AttributeSet attrs) {

        super(context, attrs);
//        setBackgroundColor(Color.parseColor("#ffffff"));
        init();
        setWillNotDraw(false);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.FILL);
        mBytes = new byte[128];
//        setBackgroundColor(Color.parseColor("#ffffff"));
        for (int i=1;i<128;i++){
            mBytes[i]=0;
        }
    }

    public void updateVisualizer(byte[] bytes) {
        mBytes = bytes;
//        waves=new int[128];
//        for (int i=1;i<128;i++){
//            waves[i]=bytes[i];
//        }
        invalidate();
//        forceLayout();
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        if (mBytes == null) {
//            return;
//        }
//        if (mPoints == null || mPoints.length < mBytes.length * 4) {
//
//            mPoints = new float[mBytes.length * 4];
//            mRect.set(0, 0, getWidth(), getHeight() - 50);
//            for (int i = 0; i < 9; i++) {
//                if (mBytes[i] < 0)
//                    mBytes[i] = 127;
//                mPoints[i * 4] = mRect.width() * i / 9;
//                mPoints[i * 4 + 1] = mRect.height() / 2;
//                mPoints[i * 4 + 2] = mRect.width() * i / 9;
//                mPoints[i * 4 + 3] = 2 + mRect.height() / 2 + mBytes[i];
//            }
//            canvas.drawLines(mPoints, mPaint);
//        }
//        postInvalidateDelayed(1000);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        w = getWidth();
        h = getHeight();
        rw = w / 128;
        for (int i = 0; i < 128; i++) {
                rh = h / 512 * (mBytes[i] + 128);
            canvas.drawRect(rw * i, h/2 - rh, rw * (i + 1),h/2 + rh, mPaint);
        }
//            canvas.drawLines(mPoints, mPaint);
    postInvalidateDelayed(1000);

}

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = getMeasuredWidth();
        mRectHeight = getMeasuredHeight();
//        mRectWidth = (int) (mViewWidth * 0.6 / mRectCount);
//        mRectWidth=getMeasuredWidth();
        mLinearGradient = new LinearGradient(0, 0, 0, mRectHeight,
                new int[]{Color.RED,Color.YELLOW,Color.GREEN},null, Shader.TileMode.CLAMP);
        //给画笔设置Shader
        mPaint.setShader(mLinearGradient);
    }

}
