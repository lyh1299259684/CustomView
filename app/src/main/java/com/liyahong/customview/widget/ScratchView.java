package com.liyahong.customview.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.liyahong.customview.R;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/23 0023 10:45
 *
 * 自定义View实现刮刮乐
 */

public class ScratchView extends View {

    private Path mPath;
    private Paint mPaint;
    private Canvas mCanvas;
    private Bitmap dstBitmap, srcBitmap;

    private int resId;

    public ScratchView(Context context) {
        this(context, null);
    }

    public ScratchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScratchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScratchView, defStyleAttr, 0);

        resId = typedArray.getResourceId(R.styleable.ScratchView_src, R.mipmap.ic_launcher);

        typedArray.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setAlpha(0);
        mPaint.setStrokeWidth(35);
        mPaint.setStyle(Paint.Style.STROKE);

        dstBitmap = decodeBitmapFormRes(getResources(), resId, getWidth(), 600);
        srcBitmap = Bitmap.createBitmap(dstBitmap.getWidth(), dstBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(srcBitmap);
        mCanvas.drawColor(Color.GRAY);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(dstBitmap, 0, 0, null);
        drawPath();
        canvas.drawBitmap(srcBitmap, 0, 0, null);
    }

    private void drawPath(){
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:   //手指抬起
                mPath.reset();  //清空路径
                mPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:   //正在滑动
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
            width = 300;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 300;
        }

        setMeasuredDimension(width, height);
    }

    /**
     * 缩放图片
     * @return bitmap
     */
    private Bitmap decodeBitmapFormRes(Resources resources, int resId, int targetWidth, int targetHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateInSample(options, targetWidth, targetHeight);
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    private int calculateInSample(BitmapFactory.Options options, int targetWidth, int targetHeight){
        if (targetWidth <= 0 || targetHeight <= 0) {
            return 1;
        }

        int sample = 1;

        final int rawWidth = options.outWidth;
        final int rawHeight = options.outHeight;

        if (rawWidth > targetWidth || rawHeight > targetHeight) {
            final int halfWidth = rawWidth / 2;
            final int halfHeight = rawHeight / 2;

            while ((halfWidth / sample >= targetWidth) && (halfHeight / sample >= targetHeight)) {
                sample *= 2;
            }
        }

        return sample;
    }
}
