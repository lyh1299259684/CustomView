package com.liyahong.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.liyahong.customview.R;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/6/12 0012 16:02
 */

public class DrawArc extends View {

    private Paint mPaintLeft, mPaintTop, mPaintRight, mPaintBottom;
    private RectF mRect;
    private int mLeftColor, mTopColor, mRightColor, mBottomColor;
    private boolean useCenter;

    public DrawArc(Context context) {
        this(context, null);
    }

    public DrawArc(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawArc(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawArc, defStyleAttr, 0);

        mLeftColor = typedArray.getColor(R.styleable.DrawArc_leftColor, Color.YELLOW);
        mTopColor = typedArray.getColor(R.styleable.DrawArc_topColor, Color.YELLOW);
        mRightColor = typedArray.getColor(R.styleable.DrawArc_rightColor, Color.YELLOW);
        mBottomColor = typedArray.getColor(R.styleable.DrawArc_bottomColor, Color.YELLOW);
        useCenter = typedArray.getBoolean(R.styleable.DrawArc_useCenter, false);

        typedArray.recycle();

        init();
    }

    private void init() {
        mPaintLeft = new Paint();
        mPaintLeft.setColor(mLeftColor);

        mPaintTop = new Paint();
        mPaintTop.setColor(mTopColor);

        mPaintRight = new Paint();
        mPaintRight.setColor(mRightColor);

        mPaintBottom = new Paint();
        mPaintBottom.setColor(mBottomColor);

        mRect = new RectF(0f, 0f, 500f, 500f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * RectF：矩阵
         * startAngle：从哪个角度开始绘制
         * sweepAngle：绘制多少度
         * useCenter：是否保留绘制痕迹
         * paint：画笔
         * drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
         */
        canvas.drawArc(mRect, 0, 90, useCenter, mPaintRight);
        canvas.drawArc(mRect, 90, 90, useCenter, mPaintBottom);
        canvas.drawArc(mRect, 180, 90, useCenter, mPaintLeft);
        canvas.drawArc(mRect, 270, 90, useCenter, mPaintTop);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = 500;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 500;
        }

        setMeasuredDimension(width, height);
    }
}
