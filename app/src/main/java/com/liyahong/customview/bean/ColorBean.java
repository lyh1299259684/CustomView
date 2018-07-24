package com.liyahong.customview.bean;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/24 0024 08:37
 */

public class ColorBean {

    private boolean isSelected;
    private String color;

    public ColorBean(String color) {
        this.color = color;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public ColorBean setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
