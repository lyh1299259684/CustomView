package com.liyahong.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.liyahong.customview.R;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/25 0025 09:58
 *
 * 学习BitmapShader
 */

public class CustomBitmapShader extends View{

    private int resId;
    private Paint mPaint;
    private Bitmap bitmap;

    public CustomBitmapShader(Context context) {
        this(context, null);
    }

    public CustomBitmapShader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBitmapShader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomBitmapShader, defStyleAttr, 0);

        resId = typedArray.getResourceId(R.styleable.CustomBitmapShader_src, R.mipmap.ic_launcher);

        typedArray.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = BitmapFactory.decodeResource(getResources(), resId);
        /**
         * Shader.TileMode 一共三种类型
         *      1.CLAMP 拉伸，图片的最后的一个像素，不断重复
         *      2.REPEAT 重复，横向、纵向不断重复
         *      3.MIRROR 镜像，横向不断翻转重复，纵向不断翻转重复
         */
        final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int x = getWidth() / 2;
        int y = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 2;
        canvas.drawCircle(x, y, radius, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getPaddingLeft() + min + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + min + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }
}
