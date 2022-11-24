package com.ydh.couponstao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ydh.couponstao.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PingAdapter extends RecyclerView.Adapter {

    private List<String> stringList = Collections.synchronizedList(new ArrayList<String>());

    public void addString(String string){
        int currentPosition = stringList.size();
        stringList.add(currentPosition,string);
        notifyItemChanged(currentPosition);

    }

    public List<String> getStringList(){
        return stringList;
    }


    public void clear(){
        stringList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ping_item,parent,false);
        return new ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemView)holder).mTvText.setText(stringList.get(position));

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ItemView extends RecyclerView.ViewHolder{

        public TextView mTvText;
        public ItemView(@NonNull View itemView) {
            super(itemView);
            mTvText = itemView.findViewById(R.id.tv_ping_text);
        }
    }

}
