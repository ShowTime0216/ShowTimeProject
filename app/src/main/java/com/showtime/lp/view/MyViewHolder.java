package com.showtime.lp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author: Trouble Maker
 * @date: 2017/9/5 0005
 * @Description:
 */
public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public MyViewHolder(View convertView) {
        super(convertView);
    }

    public MyViewHolder(View convertView, OnItemClickListener listener1) {
        super(convertView);
        this.mOnItemClickListener = listener1;
    }

    public MyViewHolder(View convertView, OnItemClickListener listener1, OnItemLongClickListener listener2) {
        super(convertView);
        this.mOnItemClickListener = listener1;
        this.mOnItemLongClickListener = listener2;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(v, getLayoutPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null)
            mOnItemLongClickListener.onItemLongClick(v, getLayoutPosition());
        return true;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int position);
    }
}
