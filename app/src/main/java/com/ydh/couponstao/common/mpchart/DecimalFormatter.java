package com.ydh.couponstao.common.mpchart;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by ydh on 2022/10/20
 */
public class DecimalFormatter extends ValueFormatter {
    private DecimalFormat format;

    public DecimalFormatter() {
        format = new DecimalFormat("###,###,##0.00");
    }

    @Override
    public String getFormattedValue(float value) {
        return format.format(value) + "$";
    }
}