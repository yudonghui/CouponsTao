package com.ydh.couponstao.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;


import com.google.zxing.ResultPoint;
import com.ydh.couponstao.R;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by ydh on 2022/1/4
 */
public class ViewfinderView extends View {


    //刷新界面的时间
    private static final long ANIMATION_DELAY = 10L;
    private static final int OPAQUE = 0xFF;

    //四个绿色边角对应的长度
    private int ScreenRate;
    //四个绿色边角对应的宽度
    private static final int CORNER_WIDTH = 10;

    //扫描框中中间线的宽度
    private static final int MiDDLE_LINE_WIDTH = 6;

    //扫描框中的中间线与扫描框左右的间隙
    private static final int MIDDLE_LINE_PADDING = 5;

    //中间那条线每次刷新移动的距离
    private static final int SPEEN_DISTANCE = 5;

    //手机的屏幕密度
    private static float density;

    //字体大小
    private static final int TEXT_SIZE = 16;

    //字体距离扫描框下面的距离
    private static final int TEXT_PADDING_TOP = 30;

    //画笔对象的引用
    private Paint paint;

    //中间滑动线的最顶端位置
    private int slideTop;
    //中间滑动线的最底端位置
    private int slideBottom;

    private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;

    private final int resultPointColor;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;

    boolean isFirst;
    private Context mContext;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        density = context.getResources().getDisplayMetrics().density;
        //像素转化成dp
        ScreenRate = (int) (20 * density);

        paint = new Paint();
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.transparent);
        resultColor = resources.getColor(R.color.color_black);

        resultPointColor = resources.getColor(R.color.color_theme);
        possibleResultPoints = new HashSet<>(5);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        //获取屏幕的宽和高
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (resultBitmap == null)
            drawableToBitamp(ContextCompat.getDrawable(mContext, R.mipmap.qrcode_scan_line));
        //中间的扫描框,想要修改扫描框的大小可以去CameraManager里面修改
        Rect frame = new Rect(0, 0, width, height);
        if (frame == null) {
            return;
        }

        //初始化中间线滑动的最上边和最下边
        if (!isFirst) {
            isFirst = true;
            slideTop = frame.top;
            slideBottom = frame.bottom;
        }

        paint.setColor(resultBitmap != null ? resultColor : maskColor);

        //画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
        //扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        //画扫描框边上的角，总共8个部分
        paint.setColor(getResources().getColor(R.color.color_theme));
        canvas.drawRect(frame.left, frame.top, frame.left + ScreenRate,
                frame.top + CORNER_WIDTH, paint);
        canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH, frame.top
                + ScreenRate, paint);
        canvas.drawRect(frame.right - ScreenRate, frame.top, frame.right,
                frame.top + CORNER_WIDTH, paint);
        canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right, frame.top
                + ScreenRate, paint);
        canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left
                + ScreenRate, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - ScreenRate,
                frame.left + CORNER_WIDTH, frame.bottom, paint);
        canvas.drawRect(frame.right - ScreenRate, frame.bottom - CORNER_WIDTH,
                frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom - ScreenRate,
                frame.right, frame.bottom, paint);


        //绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
        slideTop += SPEEN_DISTANCE;
        if (slideTop >= frame.bottom) {
            slideTop = frame.top;
        }
        // canvas.drawRect(frame.left + MIDDLE_LINE_PADDING, slideTop - MiDDLE_LINE_WIDTH / 2, frame.right - MIDDLE_LINE_PADDING, slideTop + MiDDLE_LINE_WIDTH / 2, paint);
        canvas.drawBitmap(resultBitmap, frame.left + MIDDLE_LINE_PADDING, slideTop - MiDDLE_LINE_WIDTH / 2, paint);
        //只刷新扫描框的内容，其他地方不刷新
        postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
                frame.right, frame.bottom);
    }

    public void drawableToBitamp(Drawable drawable) {
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config = drawable.getOpacity()
                != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        resultBitmap = Bitmap.createBitmap(getWidth(), 8, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(resultBitmap);
        drawable.setBounds(0, 0, getWidth(), 8);
        drawable.draw(canvas);
    }

}
