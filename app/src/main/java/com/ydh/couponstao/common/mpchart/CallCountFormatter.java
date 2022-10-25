package com.ydh.couponstao.common.mpchart;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * Created by ydh on 2022/10/20
 */
public class CallCountFormatter extends ValueFormatter {

    private final BarLineChartBase<?> mChart;

    public CallCountFormatter(BarLineChartBase<?> chart) {
        this.mChart = chart;

    }

    @Override
    public String getFormattedValue(float value) {

        return value + "数据";
//        if(value==0.0 || value == 6.0){
//            return "" ;
//        }else {
//            return "13685624925";
//        }
    }
}