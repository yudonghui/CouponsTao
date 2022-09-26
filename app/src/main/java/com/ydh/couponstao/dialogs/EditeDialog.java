package com.ydh.couponstao.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ydh.couponstao.R;


/**
 * Created by ydh on 2021/1/23
 */
public class EditeDialog {
    private TextView mTvConfirm;
    private Dialog mHintDialog;
    private EditText mEtInput;

    /**
     * @param mode 1 只能输入数字，2输入所有类型
     */
    public EditeDialog(final Context mContext, String hint, int mode, final EditInterface mListener) {
        mHintDialog = new Dialog(mContext, R.style.HintDialog);
        mHintDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mHintDialog.getWindow().setType(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        View view = View.inflate(mContext, R.layout.dialog_edite, null);
        mEtInput = (EditText) view.findViewById(R.id.et_input);
        mTvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        if (mode == 1) {
            mEtInput.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        } else if (mode == 2) {
            mEtInput.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        }
        mEtInput.setHint(hint);
        mHintDialog.setContentView(view);
        mHintDialog.show();
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                String s = mEtInput.getText().toString();
                mListener.onClick(s);
            }
        });
    }

    public interface EditInterface {
        void onClick(String s);
    }
}
