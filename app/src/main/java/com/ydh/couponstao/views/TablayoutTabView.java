package com.ydh.couponstao.views;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Android on 2017/12/22.
 */

public class TablayoutTabView {
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        try {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) tabs.getChildAt(0);


            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                Field mTextViewField = tabView.getClass().getDeclaredField("textView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);

                tabView.setPadding(0, 0, 0, 0);

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                int width = 0;
                width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }

                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = leftDip;
                params.rightMargin = rightDip;
                tabView.setLayoutParams(params);

                tabView.invalidate();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
    public void setIndicator(TabLayout tabs) {
        try {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) tabs.getChildAt(0);


            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                Field mTextViewField = tabView.getClass().getDeclaredField("textView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);
                tabView.setPadding(0, 0, 0, 0);

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                int width = 0;
                width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }

                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width;
                tabView.setLayoutParams(params);

                tabView.invalidate();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
    public void setIndicator(TabLayout tabs, List<String> mTitleList) {
        try {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) tabs.getChildAt(0);


            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                Field mTextViewField = tabView.getClass().getDeclaredField("textView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);
                mTextView.setText(mTitleList.get(i));
                tabView.setPadding(0, 0, 0, 0);

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                int width = 0;
                width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }

                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width;
                tabView.setLayoutParams(params);

                tabView.invalidate();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
