package com.ydh.couponstao.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.ydh.couponstao.R;
import com.ydh.couponstao.interfaces.ViewInterface;

/**
 * 带删除按钮的文本框
 */
@SuppressLint("AppCompatCustomView")
public class ClearEditText extends EditText {

    private Drawable drawable;
    private Context context;
    private boolean tag;//焦点

    public ClearEditText(Context context) {
        super(context);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.editTextStyle);
        this.context = context;
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private ViewInterface mListener;

    public void setListener(ViewInterface mListener) {
        this.mListener = mListener;
    }

    private void init() {
        drawable = getCompoundDrawables()[2];
        if (null == drawable) {
            drawable = ContextCompat.getDrawable(context, R.mipmap.fork_icon);
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        // 默认设置隐藏图标
        setClearIconVisible(false);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                    if (mListener != null) {
                        mListener.onClick(null);
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (drawable != null) {
            if (text.length() > 0) {
                setClearIconVisible(true);
            } else {
                setClearIconVisible(false);
            }
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
//        tag = focused;
        if (focused) {
            if (this.getText().toString().length() > 0) {
                setClearIconVisible(true);
            }
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏
     *
     * @param visible 显示标记
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? drawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

}
