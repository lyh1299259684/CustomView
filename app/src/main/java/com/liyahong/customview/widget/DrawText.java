package com.liyahong.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.liyahong.customview.R;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/5 0005 11:48
 *
 * 绘制文字
 */

public class DrawText extends View {

    private String text;
    private int textColor;
    private int textSize;

    private Paint mPaint;
    private Rect mTextBound;

    public DrawText(Context context) {
        this(context, null);
    }

    public DrawText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawText, defStyleAttr, 0);

        text = typedArray.getString(R.styleable.DrawText_text);
        textColor = typedArray.getColor(R.styleable.DrawText_textColor, Color.BLACK);
        textSize = typedArray.getDimensionPixelSize(R.styleable.DrawText_textSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));

        typedArray.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);

        mTextBound = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), mTextBound);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(text, getWidth() / 2 - mTextBound.width() / 2, getHeight() / 2 + mTextBound.height() / 2, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        if (widthMode == MeasureSpec.EXACTLY) {  //精准模式 (match_parent or custom)
            width = widthSize;
        } else {
            float textWidth = mTextBound.width();
            width = (int) (getPaddingLeft() + getPaddingRight() + textWidth);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            float textHeight = mTextBound.height();
            height = (int) (getPaddingTop() + getPaddingBottom() + textHeight);
        }

        setMeasuredDimension(width, height);
    }
}