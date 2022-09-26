package com.ydh.couponstao.interfaces;

import android.view.View;

public interface OnRvItemListener {
    void itemClick(int position);

    void itemChildClick(View view, int position);
}
