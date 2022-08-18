package com.ydh.couponstao.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Created by zhengluping on 2018/12/15
 */
public class WaterMarkBg extends Drawable {

    private Paint paint = new Paint();
    private List<String> labels;
    private Context context;
    private int degress;//角度
    private int fontSize;//字体大小 单位sp

    /**
     * 初始化构造
     *
     * @param context  上下文
     * @param labels   水印文字列表 多行显示支持
     * @param degress  水印角度
     * @param fontSize 水印文字大小
     */
    public WaterMarkBg(Context context, List<String> labels, int degress, int fontSize) {
        this.labels = labels;
        this.context = context;
        this.degress = degress;
        this.fontSize = fontSize;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int width = getBounds().right;
        int height = getBounds().bottom;

        //  paint.setColor(Color.parseColor("#20979dbc"));//这里设置透明度 华为部分手机会出现文字是黑色的情况
        paint.setColor(Color.parseColor("#000000"));
        paint.setAntiAlias(true);
        paint.setTextSize(sp2px(context, fontSize));
        canvas.save();
        canvas.rotate(degress);
        float textWidth = paint.measureText(labels.get(0));
        int index = 0;
        for (int positionY = height / 10; positionY <= height; positionY += height / 10 + 80) {
            float fromX = -width + (index++ % 2) * textWidth;
            for (float positionX = fromX; positionX < width; positionX += textWidth * 2) {
                int spacing = 0;//间距
                for (String label : labels) {
                    canvas.drawText(label, positionX, positionY + spacing, paint);
                    spacing = spacing + 50;
                }

            }
        }
        canvas.restore();
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
