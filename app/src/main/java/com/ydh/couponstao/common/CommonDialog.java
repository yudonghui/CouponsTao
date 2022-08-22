package com.ydh.couponstao.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydh.couponstao.R;
import com.ydh.couponstao.interfaces.ViewInterface;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.Strings;
import com.ydh.couponstao.utils.ZXingUtils;

/**
 * Created by ydh on 2022/8/17
 */
public class CommonDialog {
    public Dialog mHintDialog;
    private ImageView mIvCode;
    private TextView mTvMessage;
    private TextView mTvMessage2;
    private TextView mTvLeft;
    private TextView mTvRight;
    private Context mContext;

    public CommonDialog(Context mContext, Builder builder) {
        this.mContext = mContext;
        mHintDialog = new Dialog(mContext, R.style.HintDialog);
        mHintDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(mContext, R.layout.dialog_common, null);
        mIvCode = (ImageView) view.findViewById(R.id.iv_code);
        mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        mTvMessage2 = (TextView) view.findViewById(R.id.tv_message2);
        mTvLeft = view.findViewById(R.id.tv_left);
        mTvRight = view.findViewById(R.id.tv_right);
        initView(builder);
        mHintDialog.setContentView(view);
        mHintDialog.show();
        dissmissListener(builder);
    }

    public void setIvCode(String url) {
        int resize = CommonUtil.dp2px(200);
        Bitmap image = ZXingUtils.createImage(url, resize, resize, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
        mIvCode.setImageBitmap(image);
    }

    private void dissmissListener(final Builder builder) {
        mHintDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (builder.mDismissListener != null) {
                    builder.mDismissListener.onClick(null);
                }
            }
        });
    }

    private void initView(Builder builder) {
        if (TextUtils.isEmpty(builder.message)) {
            mTvMessage.setText(Strings.getString(builder.messageSpan));
        } else {
            mTvMessage.setText(builder.message);
            mTvMessage2.setText(builder.message2);
        }
        mTvLeft.setText(TextUtils.isEmpty(builder.left) ? "取消" : builder.left);
        mTvRight.setText(TextUtils.isEmpty(builder.right) ? "确认" : builder.right);
        mTvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                if (builder.mLeftListener != null) {
                    builder.mLeftListener.onClick(v);
                }
            }
        });
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                if (builder.mRightListener != null) {
                    builder.mRightListener.onClick(v);
                }
            }
        });
    }

    public static class Builder {
        /**
         * 正常中间是显示文本
         */
        private String message;
        private String message2;
        private String left;
        private String right;
        private SpannableString messageSpan;
        private ViewInterface mLeftListener;
        private ViewInterface mRightListener;
        private ViewInterface mDismissListener;

        public Builder dissmiss(ViewInterface mDismissListener) {
            this.mDismissListener = mDismissListener;
            return this;
        }

        public Builder leftBtn(String left, ViewInterface mLeftListener) {
            this.left = left;
            this.mLeftListener = mLeftListener;
            return this;
        }

        public Builder rightBtn(String right, ViewInterface mRightListener) {
            this.right = right;
            this.mRightListener = mRightListener;
            return this;
        }

        public Builder leftBtn(String left) {
            this.left = left;
            return this;
        }

        public Builder rightBtn(String right) {
            this.right = right;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder message2(String message2) {
            this.message2 = message2;
            return this;
        }

        public Builder message(SpannableString messageSpan) {
            this.messageSpan = messageSpan;
            return this;
        }

        public CommonDialog build(Context mContext) {
            return new CommonDialog(mContext, this);
        }
    }
}
