package com.ydh.couponstao.common.mpchart;

import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * Created by ydh on 2022/10/20
 */
public class RadarChartFormatter extends ValueFormatter {

    private final String[] mActivities = new String[]{"Burger", "Steak", "Salad", "Pasta", "Pizza"};
    @Override
    public String getFormattedValue(float value) {
        return mActivities[(int) value % mActivities.length];
    }
}