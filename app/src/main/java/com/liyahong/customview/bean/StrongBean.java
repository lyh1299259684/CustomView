package com.liyahong.customview.bean;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/24 0024 09:29
 *
 * 字体粗细
 */

public class StrongBean {

    private boolean isSelected;
    private int resId;
    private int strong;

    public StrongBean(int strong, int resId) {
        this.resId = resId;
        this.strong = strong;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public int getStrong() {
        return strong;
    }

    public void setStrong(int strong) {
        this.strong = strong;
    }

    public StrongBean setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
