package com.ydh.couponstao.common.mpchart;

import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * Created by ydh on 2022/10/20
 */
public class XAxisValueFormatter extends ValueFormatter {
    private String[] xStrs = new String[]{"春", "夏", "秋", "冬","寒", "来", "署", "往"};
    @Override
    public String getFormattedValue(float value) {
        int position = (int) value;
//        if (position >= 4) {
//            position = 0;
//        }
        return xStrs[position];
    }
}
