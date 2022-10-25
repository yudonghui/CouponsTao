package com.ydh.couponstao.common.mpchart;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * Created by ydh on 2022/10/20
 */
public class CallCountValueFormatter extends ValueFormatter {
    @Override
    public String getBarLabel(BarEntry barEntry) {
        String data = (String) barEntry.getData();
        return data;
    }
}