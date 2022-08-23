package com.ydh.couponstao.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.ydh.couponstao.R;
import com.ydh.couponstao.utils.CommonUtil;

/**
 * Created by ydh on 2022/8/23
 */
public class DashView extends View {
    private Context mContext;
    private Paint paint;
    private int integer;//0横向虚线，1竖向虚线
    private int marginLeft;
    private int marginRight;
    private int marginTop;
    private int marginBottom;

    public DashView(Context context) {
        this(context, null);
        mContext = context;
    }

    public DashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }

    public DashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        int colorDefault = context.getResources().getColor(R.color.color_white);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashView);
        integer = typedArray.getInteger(R.styleable.DashView_dv_orientation, 0);
        int color = typedArray.getColor(R.styleable.DashView_dv_color, colorDefault);
        int paintWidth = typedArray.getDimensionPixelSize(R.styleable.DashView_dv_width, CommonUtil.dp2px(1));
        marginLeft = typedArray.getDimensionPixelSize(R.styleable.DashView_dv_margin_left, 0);
        marginRight = typedArray.getDimensionPixelSize(R.styleable.DashView_dv_margin_right, 0);
        marginTop = typedArray.getDimensionPixelSize(R.styleable.DashView_dv_margin_top, 0);
        marginBottom = typedArray.getDimensionPixelSize(R.styleable.DashView_dv_margin_bottom, 0);
        typedArray.recycle();
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(paintWidth);
        paint.setPathEffect(new DashPathEffect(new float[]{6, 6}, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (paint != null) {
            canvas.drawLine(marginLeft, marginTop, getWidth() - marginRight, getHeight() - marginBottom, paint);

        }
    }
}
