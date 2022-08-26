package com.ydh.couponstao.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydh.couponstao.R;
import com.ydh.couponstao.activitys.SearchTbActivity;
import com.ydh.couponstao.utils.ClipboardUtils;
import com.ydh.couponstao.utils.Strings;

/**
 * Created by ydh on 2022/8/26
 */
public class CheckCopyDialog {
    private Context mContext;
    private Dialog mDialog;
    private ImageView mIvDelete;
    private TextView mTvMessage;
    private TextView mTvLook;

    public CheckCopyDialog(Context mContext) {
        this.mContext = mContext;
        mDialog = new Dialog(mContext, R.style.HintDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(mContext, R.layout.dialog_check_copy, null);
        mIvDelete = (ImageView) view.findViewById(R.id.iv_delete);
        mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        mTvLook = (TextView) view.findViewById(R.id.tv_look);
        mDialog.setContentView(view);
        mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ClipboardUtils.setClipboardNo("");
            }
        });
    }

    public void show(String content) {
        mTvMessage.setText(Strings.getString(content));
        mTvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchTbActivity.class);
                intent.putExtra("searchContent", content);
                mContext.startActivity(intent);
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }
}
