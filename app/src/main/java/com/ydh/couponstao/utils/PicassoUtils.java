package com.ydh.couponstao.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ydh.couponstao.R;

public class PicassoUtils {
    public static void setNetImg(String url, Context mContext, final ImageView mImageView) {
        LogUtils.e("图片加载接口：" + url);
        if (TextUtils.isEmpty(url)) {
            mImageView.setVisibility(View.GONE);
        } else {
            Picasso.with(mContext)
                    .load(url)
                    .error(R.mipmap.load_fail_small)
                    .config(Bitmap.Config.RGB_565)
                    .into(mImageView);
        }
    }

}
