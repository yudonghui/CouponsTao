package com.ydh.couponstao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ydh.couponstao.R;
import com.ydh.couponstao.entitys.IpEntiyty;
import com.ydh.couponstao.interfaces.SearchInterface;

import java.util.List;

/**
 * Date:2023/6/20
 * Time:17:56
 * author:ydh
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemView> {
    List<IpEntiyty> mDatas;
    SearchInterface mListener;

    public SearchAdapter(List<IpEntiyty> mDatas, SearchInterface mListener) {
        this.mDatas = mDatas;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_ping, parent, false);
        return new SearchAdapter.ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView holder, final int position) {
        holder.mTvText.setText(mDatas.get(position).getRemark());
        holder.mTvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class ItemView extends RecyclerView.ViewHolder {

        public TextView mTvText;

        public ItemView(@NonNull View itemView) {
            super(itemView);
            mTvText = itemView.findViewById(R.id.tv_content);
        }
    }
}
