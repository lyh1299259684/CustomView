package com.liyahong.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.liyahong.customview.R;

import javax.crypto.spec.DESedeKeySpec;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/6 0006 09:38
 *
 * 绘制圆形，添加颜色过滤器
 */

public class DrawCircleAddColorFilter extends View {

    private int defaultColor;
    private int filterColor;
    private int circleRadius;
    private Paint mPaint;

    public DrawCircleAddColorFilter(Context context) {
        this(context, null);
    }

    public DrawCircleAddColorFilter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawCircleAddColorFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawCircleAddColorFilter, defStyleAttr, 0);

        defaultColor = typedArray.getColor(R.styleable.DrawCircleAddColorFilter_defaultColor, Color.YELLOW);
        filterColor = typedArray.getColor(R.styleable.DrawCircleAddColorFilter_filterColor, Color.GREEN);
        circleRadius = typedArray.getDimensionPixelSize(R.styleable.DrawCircleAddColorFilter_circleRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()));

        typedArray.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(defaultColor);
        PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(filterColor, PorterDuff.Mode.OVERLAY);
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int x = getWidth() / 2;
        int y = getHeight() / 2;

        canvas.drawCircle(x, y, circleRadius, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = circleRadius * 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = circleRadius * 2;
        }

        if (width != height) {
            height = width;
            circleRadius = getWidth() / 2;
        }

        setMeasuredDimension(width, height);
    }
}
