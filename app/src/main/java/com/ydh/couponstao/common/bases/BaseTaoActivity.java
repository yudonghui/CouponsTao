package com.ydh.couponstao.common.bases;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;
import com.ydh.couponstao.dialogs.CheckCopyDialog;
import com.ydh.couponstao.utils.ClipboardUtils;
import com.ydh.couponstao.utils.LogUtils;

/**
 * @author zhengluping
 * @date 2018/1/16
 */
public class BaseTaoActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.e("生命周期 onRestart");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtils.e("生命周期 子线程");
                String clipboard = ClipboardUtils.getClipboard();
                if (ObjectUtils.isNotEmpty(clipboard)) {
                    new CheckCopyDialog(mContext).show(clipboard);
                }
            }
        }, 1000);
    }
}
