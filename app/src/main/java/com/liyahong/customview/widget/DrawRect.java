package com.liyahong.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/6/12 0012 14:43
 */

public class DrawRect extends View{

    private Paint mPaint;

    public DrawRect(Context context) {
        this(context, null);
    }

    public DrawRect(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0, height = 0;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            if (widthMode == MeasureSpec.AT_MOST) {
                width = getPaddingLeft() + getPaddingRight() + (getRight() - getLeft());
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            if (heightMode == MeasureSpec.AT_MOST) {
                height = getPaddingTop() + getPaddingBottom() + (getBottom() - getTop());
            }
        }

        setMeasuredDimension(width, height);
    }
}
