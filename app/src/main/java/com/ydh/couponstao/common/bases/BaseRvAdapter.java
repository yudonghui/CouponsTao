package com.ydh.couponstao.common.bases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.ydh.couponstao.interfaces.OnRvItemListener;

import java.util.List;

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;
    private LayoutInflater inflater;
    public List<T> datas;
    private int layoutId;
    protected OnItemClickListner onItemClickListner;//单击事件
    protected OnItemLongClickListner onItemLongClickListner;//长按单击事件
    protected OnRvItemListener onRvItemListener;//本项目经常用到的
    private boolean clickFlag = true;//单击事件和长单击事件的屏蔽标识

    public BaseRvAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.datas = datas;
        this.layoutId = layoutId;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = new BaseViewHolder(inflater.inflate(layoutId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, datas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    protected abstract void bindData(BaseViewHolder holder, T data, int position);

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public void setOnItemLongClickListner(OnItemLongClickListner onItemLongClickListner) {
        this.onItemLongClickListner = onItemLongClickListner;
    }

    public void setOnRvItemListener(OnRvItemListener onRvItemListener) {
        this.onRvItemListener = onRvItemListener;
    }

    public interface OnItemClickListner {
        void onItemClickListner(View v, int position);
    }

    public interface OnItemLongClickListner {
        void onItemLongClickListner(View v, int position);
    }
}
