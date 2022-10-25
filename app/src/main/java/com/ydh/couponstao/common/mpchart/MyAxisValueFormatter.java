package com.ydh.couponstao.common.mpchart;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by ydh on 2022/10/20
 */
public class MyAxisValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;
    public MyAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.000");
    }
    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + " $";
    }
}