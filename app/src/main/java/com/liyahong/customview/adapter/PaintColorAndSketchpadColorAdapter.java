package com.liyahong.customview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liyahong.customview.R;
import com.liyahong.customview.bean.ColorBean;
import com.liyahong.customview.interfaces.OnItemClickListener;

import java.util.List;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/24 0024 08:48
 *
 * 画笔颜色 和 画板颜色适配器
 */

public class PaintColorAndSketchpadColorAdapter extends RecyclerView.Adapter<PaintColorAndSketchpadColorAdapter.ViewHolder>{

    private List<ColorBean> datas;
    private Context mContext;
    private int action;
    private OnItemClickListener mOnItemClickListener;

    public PaintColorAndSketchpadColorAdapter(List<ColorBean> datas, Context mContext, int action) {
        this.datas = datas;
        this.mContext = mContext;
        this.action = action;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public void refreshAdapter(List<ColorBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_paint_sketchpad_color_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ColorBean colorBean = datas.get(position);

        holder.card_view.setCardBackgroundColor(Color.parseColor(colorBean.getColor()));
        if (colorBean.isSelected()) {
            holder.iv_selected.setVisibility(View.VISIBLE);
        } else {
            holder.iv_selected.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new OnItemClick(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CardView card_view;
        ImageView iv_selected;

        private ViewHolder(View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.card_view);
            iv_selected = itemView.findViewById(R.id.iv_selected);
        }
    }

    private class OnItemClick implements View.OnClickListener{

        private int position;

        private OnItemClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.OnItemClick(position, action);
            }
        }
    }
}
