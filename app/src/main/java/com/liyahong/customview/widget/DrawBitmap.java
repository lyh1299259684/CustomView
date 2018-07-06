package com.liyahong.customview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.liyahong.customview.R;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/5 0005 11:21
 *
 * 绘制Bitmap
 */

public class DrawBitmap extends View {

    private int resId;
    private Paint mPaint;
    private Bitmap mBitmap;

    public DrawBitmap(Context context) {
        this(context, null);
    }

    public DrawBitmap(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawBitmap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawBitmap, defStyleAttr, 0);

        resId = typedArray.getResourceId(R.styleable.DrawBitmap_src, R.mipmap.ic_launcher);

        typedArray.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth() / 2 - mBitmap.getWidth() / 2;
        int height = getHeight() / 2 - mBitmap.getHeight() / 2;
        canvas.drawBitmap(mBitmap, width, height, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        if (widthMode == MeasureSpec.EXACTLY) { //match_parent or custom
            width = widthSize;
        } else {
            width = getPaddingLeft() + getPaddingRight() + mBitmap.getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + getPaddingBottom() + mBitmap.getHeight();
        }

        //重置尺寸
        setMeasuredDimension(width, height);
    }
}
