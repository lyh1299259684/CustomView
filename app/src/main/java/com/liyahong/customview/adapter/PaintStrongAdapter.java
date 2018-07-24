package com.liyahong.customview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liyahong.customview.R;
import com.liyahong.customview.bean.ColorBean;
import com.liyahong.customview.bean.StrongBean;
import com.liyahong.customview.interfaces.OnItemClickListener;

import java.util.List;

/**
 * author: LiYaHong
 * email: happyboy183@163.com
 * create time: 2018/7/24 0024 09:42
 *
 * 画笔粗细Adapter
 */

public class PaintStrongAdapter extends RecyclerView.Adapter<PaintStrongAdapter.StrongHolder>{

    private List<StrongBean> datas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public PaintStrongAdapter(List<StrongBean> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public void refreshAdapter(List<StrongBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StrongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_paint_strong_item, parent, false);
        return new StrongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StrongHolder holder, int position) {
        StrongBean strongBean = datas.get(position);

        if (strongBean.isSelected()) {
            holder.cl_strong_item.setBackgroundColor(Color.RED);
        } else {
            holder.cl_strong_item.setBackgroundColor(Color.parseColor("#cccccc"));
        }

        holder.iv_strong.setImageResource(strongBean.getResId());

        holder.itemView.setOnClickListener(new OnItemClick(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class StrongHolder extends RecyclerView.ViewHolder{

        ConstraintLayout cl_strong_item;
        ImageView iv_strong;

        private StrongHolder(View itemView) {
            super(itemView);
            cl_strong_item = itemView.findViewById(R.id.cl_strong_item);
            iv_strong = itemView.findViewById(R.id.iv_strong);
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
                mOnItemClickListener.OnItemClick(position, 3);
            }
        }
    }
}
