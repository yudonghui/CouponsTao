package com.ydh.couponstao.activitys;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.ydh.couponstao.R;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.common.mpchart.CallCountFormatter;
import com.ydh.couponstao.common.mpchart.CallCountValueFormatter;
import com.ydh.couponstao.common.mpchart.DecimalFormatter;
import com.ydh.couponstao.common.mpchart.MyAxisValueFormatter;
import com.ydh.couponstao.common.mpchart.MyMarkerView;
import com.ydh.couponstao.common.mpchart.RadarChartFormatter;
import com.ydh.couponstao.common.mpchart.RadarMarkerView;
import com.ydh.couponstao.common.mpchart.XAxisValueFormatter;
import com.ydh.couponstao.common.mpchart.XYMarkerView;

import java.util.ArrayList;
import java.util.List;

public class MpAndroidChartActivity extends BaseActivity implements OnChartValueSelectedListener {
    private BarChart yearBarChart;
    private BarChart mBarChart;
    private HorizontalBarChart hBarChart;
    private RadarChart radarChart;
    private LineChart lineChart;
    private LineChart lineChart2;
    private LineChart lineChart3;
    private PieChart mPieChart;
   // private Typeface tf;

    protected final String[] parties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp_android_chart);
        initYearBarChart();
        initmBarChar();
        inithBarChart();
        initRadarChart();
        initLineChart();
        initLineChart2();
        initLineChart3();
        initmPieChart();
    }
    private void initmPieChart() {
        mPieChart = findViewById(R.id.mPieChart);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);

       // tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        //mPieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        mPieChart.setCenterText(generateCenterSpannableText());

        mPieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        mPieChart.setOnChartValueSelectedListener(this);

//        seekBarX.setProgress(4);
//        seekBarY.setProgress(100);

        initPieChartData(6, 100);

        mPieChart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    private void initPieChartData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) (Math.random() * range) + range / 5, parties[i % parties.length]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
       // data.setValueTypeface(tf);
        mPieChart.setData(data);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
    }

    private void initLineChart3() {
        lineChart3 = findViewById(R.id.lineChart3);
        lineChart3.setViewPortOffsets(0, 0, 0, 0);
        lineChart3.setBackgroundColor(Color.rgb(104, 241, 175));

        // no description text
        lineChart3.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart3.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart3.setDragEnabled(true);
        lineChart3.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart3.setPinchZoom(false);

        lineChart3.setDrawGridBackground(false);
        lineChart3.setMaxHighlightDistance(300);

        XAxis x = lineChart3.getXAxis();
        x.setEnabled(false);

        YAxis y = lineChart3.getAxisLeft();
//        y.setTypeface(tfLight);
        y.setLabelCount(6, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        lineChart3.getAxisRight().setEnabled(false);

        // add data


        lineChart3.getLegend().setEnabled(false);

        lineChart3.animateXY(2000, 2000);

        setLinChartData3();

        // don't forget to refresh the drawing
        lineChart3.invalidate();
    }

    private void setLinChartData3() {
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            float val = (float) (Math.random() * (30 + 1)) + 20;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (lineChart3.getData() != null &&
                lineChart3.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart3.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart3.getData().notifyDataChanged();
            lineChart3.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lineChart3.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
//            data.setValueTypeface(tfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            lineChart3.setData(data);
        }
    }

    private void initLineChart2() {
        lineChart2 = findViewById(R.id.lineChart2);

        // background color
        lineChart2.setBackgroundColor(Color.WHITE);

        // disable description text
        lineChart2.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart2.setTouchEnabled(true);

        // set listeners
        lineChart2.setOnChartValueSelectedListener(this);
        lineChart2.setDrawGridBackground(false);

        // create marker to display box when values are selected
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // Set the marker to the chart
        mv.setChartView(lineChart2);
        lineChart2.setMarker(mv);

        // enable scaling and dragging
        lineChart2.setDragEnabled(true);
        lineChart2.setScaleEnabled(true);

        lineChart2.setPinchZoom(true);

        XAxis xAxis = lineChart2.getXAxis();
        // vertical grid lines
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        YAxis yAxis = lineChart2.getAxisLeft();
        // disable dual axis (only use LEFT axis)
        lineChart2.getAxisRight().setEnabled(false);

        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f);

        // axis range
        yAxis.setAxisMaximum(200f);
        yAxis.setAxisMinimum(-50f);

        LimitLine llXAxis = new LimitLine(9f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);
//        llXAxis.setTypeface(tfRegular);

        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
//        ll1.setTypeface(tfRegular);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
//        ll2.setTypeface(tfRegular);

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true);
        xAxis.setDrawLimitLinesBehindData(true);

        // add limit lines
        yAxis.addLimitLine(ll1);
        yAxis.addLimitLine(ll2);
        //xAxis.addLimitLine(llXAxis);


        setLineChart1Data(45, 180);

        // draw points over time
        lineChart2.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = lineChart2.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);

    }

    private void setLineChart1Data(int count, float range) {
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) - 30;
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.shape_black_15)));
        }

        LineDataSet set1;

        if (lineChart2.getData() != null &&
                lineChart2.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart2.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            lineChart2.getData().notifyDataChanged();
            lineChart2.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lineChart2.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            lineChart2.setData(data);
        }
    }

    private void initLineChart() {
        lineChart = findViewById(R.id.lineChart);
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.getDescription().setEnabled(false);
        lineChart.setBackgroundColor(Color.WHITE);

        //自定义适配器，适配于X轴
//        IAxisValueFormatter xAxisFormatter = new XAxisValueFormatter();
        XAxisValueFormatter xAxisFormatter = new XAxisValueFormatter();

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xAxisFormatter);


        MyAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = lineChart.getAxisLeft();

        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);

        lineChart.getAxisRight().setEnabled(false);

        setLineChartData();


        lineChart.animateY(3000);//动画时长
    }

    private void setLineChartData() {
        //填充数据，在这里换成自己的数据源
        List<Entry> valsComp1 = new ArrayList<>();
        List<Entry> valsComp2 = new ArrayList<>();

        valsComp1.add(new Entry(0, 2));
        valsComp1.add(new Entry(1, 4));
        valsComp1.add(new Entry(2, 0));
        valsComp1.add(new Entry(3, 2));
        valsComp1.add(new Entry(4, 10));
        valsComp1.add(new Entry(5, 7));
        valsComp1.add(new Entry(6, 5));
        valsComp1.add(new Entry(7, 2));

        //这里，每重新new一个LineDataSet，相当于重新画一组折线
        //每一个LineDataSet相当于一组折线。比如:这里有两个LineDataSet：setComp1，setComp2。
        //则在图像上会有两条折线图，分别表示公司1 和 公司2 的情况.还可以设置更多
        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1 ");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColor(Color.BLUE);
        setComp1.setDrawCircles(false);
        setComp1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

//        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2 ");
//        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
//        setComp2.setDrawCircles(true);
//        setComp2.setColor(getResources().getColor(R.color.colorAccent));
//        setComp2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
//        dataSets.add(setComp2);

        LineData lineData = new LineData(dataSets);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void initRadarChart() {
        radarChart = findViewById(R.id.radarChart);

        radarChart.setBackgroundColor(Color.rgb(60, 65, 82));
        radarChart.getDescription().setEnabled(false);

        radarChart.setWebLineWidth(1f);
        radarChart.setWebColor(Color.LTGRAY);
        radarChart.setWebLineWidthInner(1f);
        radarChart.setWebColorInner(Color.LTGRAY);
        radarChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
        mv.setChartView(radarChart); // For bounds control
        radarChart.setMarker(mv); // Set the marker to the chart

        setRadarChartData();

        radarChart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        XAxis xAxis = radarChart.getXAxis();
//        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new RadarChartFormatter());

        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = radarChart.getYAxis();
//        yAxis.setTypeface(tfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = radarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setTypeface(tfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);

    }

    private void setRadarChartData() {
        float mul = 80;
        float min = 20;
        int cnt = 5;

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mul) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mul) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "This Week");
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
//        data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        radarChart.setData(data);
        radarChart.invalidate();
    }


    private void initmBarChar() {
        mBarChart = (BarChart) findViewById(R.id.mBarChart);

        //设置柱状图点击的时候，的回调函数
        mBarChart.setOnChartValueSelectedListener(this);
        //柱状图的阴影
        mBarChart.setDrawBarShadow(false);
        //设置柱状图Value值显示在柱状图上方 true 为显示上方，默认false value值显示在柱状图里面
        mBarChart.setDrawValueAboveBar(true);
        //Description Label 是否可见
        mBarChart.getDescription().setEnabled(false);
        // 设置最大可见Value值的数量 针对于ValueFormartter有效果
        mBarChart.setMaxVisibleValueCount(60);
        // 二指控制X轴Y轴同时放大
        mBarChart.setPinchZoom(false);
        //是否显示表格背景颜色
        mBarChart.setDrawGridBackground(false);
        //设置X轴显示文字旋转角度-60意为逆时针旋转60度
        mBarChart.getXAxis().setLabelRotationAngle(-30);


        XAxis xAxis = mBarChart.getXAxis();
        //设置X轴显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //X轴纵向分割线，一般不设置显示
        xAxis.setDrawGridLines(false);
        // X轴显示Value值的精度，与自定义X轴返回的Value值精度一致
        xAxis.setGranularity(1f);
        //X轴横坐标显示的数量
//        xAxis.setLabelCount(7);
//        //X轴最大坐标
//        xAxis.setAxisMaximum(6f);
//        //X轴最小坐标
//        xAxis.setAxisMinimum(0.5f);

        //自定义X轴
        ValueFormatter xAxisFormatter = new CallCountFormatter(mBarChart);
        xAxis.setValueFormatter(xAxisFormatter);


        //点击柱状图时显示的悬浮提示
        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mBarChart);
        mBarChart.setMarker(mv);

        //Y左边轴
        YAxis leftAxis = mBarChart.getAxisLeft();

        //设置Y左边轴显示的值 label 数量
        leftAxis.setLabelCount(8, false);
        //设置值显示的位置，我们这里设置为显示在Y轴外面
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //设置Y轴 与值的空间空隙 这里设置30f意为30%空隙，默认是10%
        leftAxis.setSpaceTop(30f);
        //设置Y轴最小坐标和最大坐标
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(80f);

        //不显示右侧的Y轴
        mBarChart.getAxisRight().setEnabled(false);


        // 设置表格标示的位置
        Legend l = mBarChart.getLegend();
        //标示坐落再表格左下方
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //标示水平朝向
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //标示在表格外
        l.setDrawInside(false);
        //样式
        l.setForm(Legend.LegendForm.SQUARE);
        //字体
        l.setFormSize(9f);
        //大小
        l.setTextSize(11f);

        //模拟数据
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(1f, 10, "微信"));
        yVals1.add(new BarEntry(2f, 20, "qq"));
        yVals1.add(new BarEntry(3f, 30, "陌陌"));
        yVals1.add(new BarEntry(4f, 40, "百度"));
        yVals1.add(new BarEntry(5f, 50, "支付宝"));
        yVals1.add(new BarEntry(6f, 60, "百词斩"));

        setData(yVals1);

        mBarChart.animateY(3000);//动画时长
    }


    private void setData(ArrayList yVals1) {
        float start = 1f;
        BarDataSet set1;
        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "号码联系次数统计");
            //设置有四种颜色
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.6f);
            data.setValueFormatter(new CallCountValueFormatter());
            //设置数据
            mBarChart.setData(data);
        }
    }

    //初始化横向柱状图
    private void inithBarChart() {
        hBarChart = findViewById(R.id.hBarChart);

        hBarChart.setOnChartValueSelectedListener(this);
        hBarChart.setDrawBarShadow(false);
        hBarChart.setDrawValueAboveBar(true);
        hBarChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        hBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        hBarChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        hBarChart.setDrawGridBackground(false);


        //自定义坐标轴适配器，设置在X轴
        DecimalFormatter formatter = new DecimalFormatter();
        XAxis xl = hBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setLabelRotationAngle(-45f);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(1f);
//        xl.setAxisMinimum(0);
        xl.setValueFormatter(formatter);

        //对Y轴进行设置
        YAxis yl = hBarChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        hBarChart.getAxisRight().setEnabled(false);

        //图例设置
        Legend l = hBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

        setHBarChartData();
        hBarChart.setFitBars(true);
        hBarChart.animateY(3000);//动画时长

    }

    private void setHBarChartData() {
        //填充数据，在这里换成自己的数据源
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        yVals1.add(new BarEntry(0, 4));
        yVals1.add(new BarEntry(1, 2));
        yVals1.add(new BarEntry(2, 6));
        yVals1.add(new BarEntry(3, 1));
        BarDataSet set1;

        if (hBarChart.getData() != null &&
                hBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) hBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            hBarChart.getData().notifyDataChanged();
            hBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "测试横向柱状图");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);

            data.setBarWidth(0.5f);

            hBarChart.setData(data);
        }
    }


    //初始化带文字的柱状图
    private void initYearBarChart() {
        //柱状图
        yearBarChart = (BarChart) findViewById(R.id.yearBarChart);
        yearBarChart.getDescription().setEnabled(false);

        //设置最大值条目，超出之后不会有值
        yearBarChart.setMaxVisibleValueCount(60);
        //分别在x轴和y轴上进行缩放
        yearBarChart.setPinchZoom(true);
        //设置剩余统计图的阴影
        yearBarChart.setDrawBarShadow(false);
        //设置网格布局
        yearBarChart.setDrawGridBackground(true);
        //通过自定义一个x轴标签来实现2,015 有分割符符bug
//        ValueFormatter custom = new MyValueFormatter("");
        //获取x轴线
        XAxis xAxis = yearBarChart.getXAxis();

        //设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置网格布局
        xAxis.setDrawGridLines(true);
        //图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setAvoidFirstLastClipping(false);
        //绘制标签  指x轴上的对应数值 默认true
        xAxis.setDrawLabels(true);
        //设置X轴上文本大小
        xAxis.setTextSize(12);

//        xAxis.setValueFormatter(custom);
        //缩放后x 轴数据重叠问题
        xAxis.setGranularityEnabled(true);
        //获取右边y标签
        YAxis axisRight = yearBarChart.getAxisRight();

        axisRight.setAxisMinimum(0f);


        //获取左边y轴的标签
        YAxis axisLeft = yearBarChart.getAxisLeft();
        //设置Y轴数值 从零开始
        axisLeft.setAxisMinimum(0f);
        yearBarChart.getAxisLeft().setDrawGridLines(false);
        //设置动画时间（X轴和Y轴都设置）
//        mBarChart.animateXY(5000, 5000);
        //只设置X轴动画时间
        yearBarChart.animateY(5000);//动画时长

        yearBarChart.getLegend().setEnabled(true);

        getYearBarData();
        //设置柱形统计  柱上的文本大小
        yearBarChart.getData().setValueTextSize(10);

        for (IDataSet set : yearBarChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
        }
    }

    private void getYearBarData() {
        ArrayList<BarEntry> values = new ArrayList<>();
        BarEntry barEntry = new BarEntry(Float.valueOf("2016"), Float.valueOf("290"));
        BarEntry barEntry1 = new BarEntry(Float.valueOf("2017"), Float.valueOf("210"));
        BarEntry barEntry2 = new BarEntry(Float.valueOf("2018"), Float.valueOf("300"));
        BarEntry barEntry3 = new BarEntry(Float.valueOf("2019"), Float.valueOf("450"));
        BarEntry barEntry4 = new BarEntry(Float.valueOf("2020"), Float.valueOf("300"));
        BarEntry barEntry5 = new BarEntry(Float.valueOf("2021"), Float.valueOf("650"));
        BarEntry barEntry6 = new BarEntry(Float.valueOf("2022"), Float.valueOf("740"));
        BarEntry barEntry7 = new BarEntry(Float.valueOf("2023"), Float.valueOf("240"));

        values.add(barEntry);
        values.add(barEntry1);
        values.add(barEntry2);
        values.add(barEntry3);
        values.add(barEntry4);
        values.add(barEntry5);
        values.add(barEntry6);
        values.add(barEntry7);

        BarDataSet set1;

        if (yearBarChart.getData() != null &&
                yearBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) yearBarChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            yearBarChart.getData().notifyDataChanged();
            yearBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "点折水");
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            yearBarChart.setData(data);
            yearBarChart.setFitBars(true);
        }

        //绘制图表
        yearBarChart.invalidate();

    }




    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.5f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

}