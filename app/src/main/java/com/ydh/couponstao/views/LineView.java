package com.ydh.couponstao.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ydh.couponstao.R;


/**
 * Created by ydh on 2022/9/26
 */
public class LineView extends View {
    private Paint paint;

    public LineView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.color_theme));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float startX, startY, stopX, stopY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint != null) {
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
    }

    public void setLocation(float startX, float startY, float stopX, float stopY) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        invalidate();
    }
}
