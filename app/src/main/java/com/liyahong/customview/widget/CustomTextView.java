package com.liyahong.customview.widget;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import com.liyahong.customview.R;
import java.util.Random;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/6/11 0011 11:37
 */

@SuppressLint("AppCompatCustomView")
public class CustomTextView extends TextView {

    //文本内容
    private String mText;
    //文本颜色
    private int mTextColor;
    //文本大小
    private int mTextSize;
    //文本背景颜色
    private int mTextBackgroundColor;
    //文本改变监听
    private OnTextChangedListener mTextChangedListener;

    /**
     * 绘制时控制绘制范围
     */
    private Paint mPaint;
    private Rect mBound;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyleAttr, 0);

        mText = typedArray.getString(R.styleable.CustomTextView_titleText);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomTextView_titleTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        mTextColor = typedArray.getColor(R.styleable.CustomTextView_titleTextColor, Color.BLACK);
        mTextBackgroundColor = typedArray.getColor(R.styleable.CustomTextView_titleBackgroundColor, Color.TRANSPARENT);

        typedArray.recycle();

        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);

        mBound = new Rect();
        //获取字体宽度的Rect，可以通过Rect.width()  Rect.height()获取
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mText = randomText();
                postInvalidateOnAnimation();

                if (mTextChangedListener != null) {
                    mTextChangedListener.onChanged(mText);
                }
            }
        });
    }

    /**
     * MeasureSpec的specMode,一共三种类型：
     * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
     * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
     * UNSPECIFIED：表示子布局想要多大就多大，很少使用
     * @param widthMeasureSpec   宽
     * @param heightMeasureSpec  高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽度模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //获取高度模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        //如果是MATCH_PARENT 或者 手动设置的宽度时，宽度就等于本身
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {  //否则要加上本身的间距
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBound);
            float textWidth = mBound.width();
            width = (int) (getPaddingLeft() + textWidth + getPaddingRight());
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBound);
            float textHeight = mBound.height();
            height = (int) (getPaddingTop() + textHeight + getPaddingBottom());
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mTextBackgroundColor);
        //绘制背景矩阵
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTextColor);
        //记住一点，宽度相减 高度相加 等于 居中
        canvas.drawText(mText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }

    /**
     * 随机生成4位数验证码
     * @return
     */
    private String randomText(){
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        while (result.length() < 4) {
            //生成10以内数字
            int randomNumber = random.nextInt(10);
            result.append(randomNumber);
        }
        return result.toString();
    }

    /**
     * 调用此方法绑定文字改变监听
     * @param textChangedListener
     */
    public void setOnTextChangedListener(OnTextChangedListener textChangedListener){
        this.mTextChangedListener = textChangedListener;
    }

    /**
     * text 文本改变监听
     */
    public interface OnTextChangedListener{
        //参数为改变后的结果
        void onChanged(String text);
    }
}