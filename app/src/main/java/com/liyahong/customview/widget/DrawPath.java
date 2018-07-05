package com.liyahong.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.liyahong.customview.R;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/5 0005 15:19
 *
 * 绘制Path
 */

public class DrawPath extends View {

    private Path mPath;
    private Paint mPaint;

    public DrawPath(Context context) {
        this(context, null);
    }

    public DrawPath(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(100, 100);
        mPath.lineTo(200, 50);
        mPath.lineTo(300, 100);
        mPath.lineTo(200, 400);
        canvas.drawPath(mPath, mPaint);
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