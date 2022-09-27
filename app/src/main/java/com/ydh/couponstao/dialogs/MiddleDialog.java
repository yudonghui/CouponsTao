package com.ydh.couponstao.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ydh.couponstao.R;
import com.ydh.couponstao.common.bases.BaseRvAdapter;
import com.ydh.couponstao.common.bases.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MiddleDialog {
    private final Dialog mDialog;
    private RecyclerView mRecyclerView;
    private TextView noData;
    private TextView tvTitle;
    private List<String> mDatasList = new ArrayList<>();
    private MiddleInterface mListener;

    public MiddleDialog(Context mContext, final List<String> mDatas, final MiddleInterface mListener) {
        mDialog = new Dialog(mContext, R.style.HintDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View view = View.inflate(mContext, R.layout.dialog_middle, null);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        noData = view.findViewById(R.id.no_data);
        tvTitle = view.findViewById(R.id.tv_title);
        mDialog.setContentView(view);
        mDialog.show();
        if (mDatas == null || mDatas.size() == 0) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
        BaseRvAdapter<String> mAdapter = new BaseRvAdapter<String>(mContext, mDatas, R.layout.item_text) {

            @Override
            protected void bindData(BaseViewHolder holder, String data, final int position) {
                TextView mTextView = holder.getView(R.id.text_view);
                mTextView.setText(TextUtils.isEmpty(data) ? "" : data);
                mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        if (mListener != null) {
                            mListener.onClick(position);
                        }
                    }
                });
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public interface MiddleInterface {
        void onClick(int position);
    }

}
