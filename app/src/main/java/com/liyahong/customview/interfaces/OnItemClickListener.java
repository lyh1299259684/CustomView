package com.liyahong.customview.interfaces;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/24 0024 09:25
 *
 * recyclerView 子条目点击回调
 */

public interface OnItemClickListener {

    /**
     * @param position  点击位置
     * @param action    1: 画笔颜色  2: 画板颜色  3: 画笔粗细
     */
    void OnItemClick(int position, int action);
}
