package com.liyahong.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.liyahong.customview.R;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/23 0023 14:47
 *
 * 自定义画板
 */

public class CustomSketchpad extends View {

    private Paint mPaint;     //签名画笔
    private Path mPath;       //绘制路径

    private int mPaintColor; //画笔颜色
    private int mPaintStrong;//画笔粗细

    public CustomSketchpad(Context context) {
        this(context, null);
    }

    public CustomSketchpad(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSketchpad(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSketchpad, defStyleAttr, 0);

        mPaintColor = typedArray.getColor(R.styleable.CustomSketchpad_paintColor, Color.BLACK);
        mPaintStrong = typedArray.getDimensionPixelSize(R.styleable.CustomSketchpad_paintStrong, (int)
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));

        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(mPaintStrong);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mPaintColor);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:  //手指摁下
                mPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:  //开始移动
                mPath.lineTo(event.getX(), event.getY());
                break;
        }
        invalidate();
        return true;
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
            width = 200;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 200;
        }

        setMeasuredDimension(width, height);
    }

    /**
     * 设置画笔颜色
     * @param color
     */
    public void setPaintColor(int color){
        mPaint.setColor(color);
        invalidate();
    }

    /**
     * 设置画笔颜色
     * @param color
     */
    public void setPaintColor(String color){
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    /**
     * 设置画笔粗细
     * @param strong
     */
    public void setPaintStrong(int strong){
        mPaint.setStrokeWidth(strong);
        invalidate();
    }

    /**
     * 清除画布中的内容
     */
    public void clearCanvas(){
        mPath.reset();
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        invalidate();
        resetCanvas();
    }

    /**
     * 还原画布为初始状态
     */
    public void resetCanvas(){
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        invalidate();
    }
}
