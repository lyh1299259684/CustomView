package com.liyahong.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.liyahong.customview.R;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/6/12 0012 15:19
 */

public class DrawCircle extends View {

    private int mRadius;
    private int mCircleColor;
    private Paint mPaint;

    public DrawCircle(Context context) {
        this(context, null);
    }

    public DrawCircle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawCircle, defStyleAttr, 0);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.DrawCircle_radius,
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));
        mCircleColor = typedArray.getColor(R.styleable.DrawCircle_circleColor, Color.CYAN);
        typedArray.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mCircleColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle((getRight() - getLeft()) / 2, (getBottom() - getTop()) / 2, mRadius, mPaint);
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
            width = getPaddingLeft() + getPaddingRight() + (getRight() - getLeft());
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingBottom() + getPaddingTop() + (getBottom() - getTop());
        }

        setMeasuredDimension(width, height);
    }
}
