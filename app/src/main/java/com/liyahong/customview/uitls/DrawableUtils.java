package com.liyahong.customview.uitls;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/5/24 0024 09:47
 *
 * 动态设置Draw
 */

public class DrawableUtils {

    public static final int LEFT_DRAW = 1;     //左边图片
    public static final int TOP_DRAW = 2;      //上边图片
    public static final int RIGHT_DRAW = 3;    //右边图片
    public static final int BOTTOM_DRAW = 4;   //底部图片

    /**
     * 设置TextView以及它子类的Drawable图片，可以设置左上右下各个边角
     * @param context
     * @param textView  可以是TextView本身或它的子类
     * @param params    图片资源，固定顺序左上右下，可以为一个或者多个
     * @param action    图片位置，想要设置到哪里，固定顺序左上右下，请使用本类中固定常量指定
     * 注意：如果左上右下都需要指定时，action可不传递
     */
    public static void setCompoundDrawables(Context context, TextView textView, int[] params, int...action){
        Drawable[] drawables = null;

        if (params != null && params.length == 4) {
            action = new int[]{LEFT_DRAW, TOP_DRAW, RIGHT_DRAW, BOTTOM_DRAW};
        }

        if (params == null || action == null) {
            throw new NullPointerException("params is null or action is null please check!");
        }

        if (params.length == 0 || action.length == 0) {
            throw new IllegalArgumentException("params is null values or action is null values please check!");
        }

        drawables = new Drawable[params.length];

        for (int i = 0; i < params.length; i++) {
            if (params[i] == -1) {
                drawables[i] = null;
                continue;
            }

            Drawable drawable = context.getResources().getDrawable(params[i]);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            drawables[i] = drawable;
        }

        switch (action.length) {
            case 1:
                switch (action[0]) {
                    case LEFT_DRAW:
                        textView.setCompoundDrawables(drawables[0], null, null, null);
                        break;
                    case TOP_DRAW:
                        textView.setCompoundDrawables(null, drawables[0], null, null);
                        break;
                    case RIGHT_DRAW:
                        textView.setCompoundDrawables(null, null, drawables[0], null);
                        break;
                    case BOTTOM_DRAW:
                        textView.setCompoundDrawables(null, null, null, drawables[0]);
                        break;
                }
                break;
            case 2:
                if (action[0] == LEFT_DRAW && action[1] == TOP_DRAW)
                {
                    textView.setCompoundDrawables(drawables[0], drawables[1], null, null);
                }
                else if (action[0] == LEFT_DRAW && action[1] == RIGHT_DRAW)
                {
                    textView.setCompoundDrawables(drawables[0], null, drawables[1], null);
                }
                else if (action[0] == LEFT_DRAW && action[1] == BOTTOM_DRAW)
                {
                    textView.setCompoundDrawables(drawables[0], null, null, drawables[1]);
                }
                else if (action[0] == TOP_DRAW && action[1] == RIGHT_DRAW)
                {
                    textView.setCompoundDrawables(null, drawables[0], drawables[1], null);
                }
                else if (action[0] == TOP_DRAW && action[1] == BOTTOM_DRAW)
                {
                    textView.setCompoundDrawables(null, drawables[0], null, drawables[1]);
                }
                else if (action[0] == RIGHT_DRAW && action[1] == BOTTOM_DRAW)
                {
                    textView.setCompoundDrawables(null, null, drawables[0], drawables[1]);
                }
                else
                {
                    throw new IllegalStateException("please check if your order of parameters is correct!");
                }
                break;
            case 3:
                if (action[0] == LEFT_DRAW && action[1] == TOP_DRAW && action[2] == RIGHT_DRAW)
                {
                    textView.setCompoundDrawables(drawables[0], drawables[1], drawables[2], null);
                }
                else if (action[0] == LEFT_DRAW && action[1] == TOP_DRAW && action[2] == BOTTOM_DRAW)
                {
                    textView.setCompoundDrawables(drawables[0], drawables[1],null, drawables[2]);
                }
                else if (action[0] == TOP_DRAW && action[1] == RIGHT_DRAW && action[2] == BOTTOM_DRAW)
                {
                    textView.setCompoundDrawables(null, drawables[0], drawables[1], drawables[2]);
                }
                else if (action[0] == LEFT_DRAW && action[1] == RIGHT_DRAW && action[2] == BOTTOM_DRAW)
                {
                    textView.setCompoundDrawables(drawables[0], null, drawables[1], drawables[2]);
                }
                else
                {
                    throw new IllegalStateException("please check if your order of parameters is correct!");
                }
                break;
            case 4:
                textView.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
                break;
        }
    }
}
