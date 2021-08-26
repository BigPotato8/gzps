package com.augurit.common.statistics.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.spinner.AMSpinner;
import com.augurit.common.R;
import com.augurit.common.statistics.model.InstallInfo;
import com.augurit.common.statistics.model.NewInstallInfo;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.statistics.view
 * @createTime 创建时间 ：2018-09-05
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class NewSignStatisticsFragment extends Fragment implements IStatisticsContract.View {

    private PieChart mPieChart;
    private PieChart mPieChart1;
    private HorizontalBarChart mHorizontalBarChart1;
    private StatisticsPresenter mPresenter;
    private AMSpinner mSp_dist;
    private ArrayList<NewInstallInfo> mChild_area1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.new_sign_statistics, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mSp_dist = view.findViewById(R.id.sp_dist);
        mPieChart = view.findViewById(R.id.pic_chart);
        mPieChart1 = view.findViewById(R.id.pic_chart1);
        mHorizontalBarChart1 = view.findViewById(R.id.hor_report_view);

        initPieChart();
        initPieChart1();
        initMPAndroidChart1();

        getData1(null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new StatisticsPresenter(this);
        mPresenter.loadAreaInfo(false);
    }

    private void initPieChart() {
        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(70f));
        strings.add(new PieEntry(30f ));

        PieDataSet dataSet = new PieDataSet(strings, "今日签到率");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.agmobile_primary_green));
        colors.add(getResources().getColor(R.color.agmobile_grey_0));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ((int) value) + "%";
            }
        });
        pieData.setValueTextColor(Color.WHITE);
        mPieChart.setData(pieData);
        mPieChart.setCenterText("总人数：512\n已签到356\n未签到156");
        mPieChart.setDrawRoundedSlices(false);
        mPieChart.invalidate();

        mPieChart.setDescription(null);
        mPieChart.setTransparentCircleRadius(0);

        // 设置表格标示的位置
        Legend l = mPieChart.getLegend();
        //标示坐落再表格左下方
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //标示水平朝向
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //标示在表格外
        l.setDrawInside(true);
        //样式
        l.setForm(Legend.LegendForm.SQUARE);
        //字体
        l.setFormSize(10f);
        //大小
        l.setTextSize(13f);

        LegendEntry legendEntry = new LegendEntry();
        legendEntry.label = "今日已签到";
        legendEntry.formColor = getResources().getColor(R.color.agmobile_primary_green);
        LegendEntry legendEntry1 = new LegendEntry();
        legendEntry1.label = "今日未签到";
        legendEntry1.formColor =getResources().getColor(R.color.agmobile_grey_0);
        List<LegendEntry> list = new ArrayList<>();
        list.add(legendEntry);
        list.add(legendEntry1);
        l.setCustom(list);

    }

    private void initPieChart1() {
        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(80f));
        strings.add(new PieEntry(20f));

        PieDataSet dataSet = new PieDataSet(strings, "昨日签到率");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.agmobile_blue));
        colors.add(getResources().getColor(R.color.agmobile_grey_0));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return ((int) value) + "%";
            }
        });
        pieData.setValueTextColor(Color.WHITE);

        mPieChart1.setData(pieData);
        mPieChart1.setCenterText("总人数：512 \n已签到401\n未签到111");
        mPieChart1.invalidate();

        mPieChart1.setTransparentCircleRadius(0);
        mPieChart1.setDescription(null);

        // 设置表格标示的位置
        Legend l = mPieChart1.getLegend();
        //标示坐落再表格左下方
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //标示水平朝向
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //标示在表格外
        l.setDrawInside(true);
        //样式
        l.setForm(Legend.LegendForm.SQUARE);
        //字体
        l.setFormSize(10f);
        //大小
        l.setTextSize(13f);

        LegendEntry legendEntry = new LegendEntry();
        legendEntry.label = "昨日已签到";
        legendEntry.formColor = getResources().getColor(R.color.agmobile_blue);
        LegendEntry legendEntry1 = new LegendEntry();
        legendEntry1.label = "昨日未签到";
        legendEntry1.formColor =getResources().getColor(R.color.agmobile_grey_0);
        List<LegendEntry> list = new ArrayList<>();
        list.add(legendEntry);
        list.add(legendEntry1);
        l.setCustom(list);
    }

    private void initMPAndroidChart1() {
        mHorizontalBarChart1.getDescription().setEnabled(false);

        //设置最大值条目，超出之后不会有值
        mHorizontalBarChart1.setMaxVisibleValueCount(Integer.MAX_VALUE);

        //分别在x轴和y轴上进行缩放
        mHorizontalBarChart1.setPinchZoom(true);
        //设置剩余统计图的阴影
        mHorizontalBarChart1.setDrawBarShadow(false);
        //设置网格布局
        mHorizontalBarChart1.setDrawGridBackground(false);

        mHorizontalBarChart1.setPinchZoom(false);
        mHorizontalBarChart1.setDoubleTapToZoomEnabled(false);

        mHorizontalBarChart1.setExtraBottomOffset(10);

        //获取x轴线
        XAxis xAxis = mHorizontalBarChart1.getXAxis();

        //设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置网格布局
        xAxis.setDrawGridLines(true);
        //图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setLabelCount(25);
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (!ListUtil.isEmpty(mChild_area1)) {
                    return mChild_area1.get((int) Math.abs(value) % mChild_area1.size()).getArea_name();
                }
                return "";
            }
        });
        //缩放后x 轴数据重叠问题
        xAxis.setGranularityEnabled(true);
        xAxis.setTextSize(16f);

        xAxis.setAxisMinimum(0f);
//        xAxis.setAxisMaximum(xValues.size());
        xAxis.setCenterAxisLabels(true);

        //获取右边y标签
        YAxis axisRight = mHorizontalBarChart1.getAxisRight();
        axisRight.setStartAtZero(true);
        axisRight.setTextSize(16f);
        //获取左边y轴的标签
        YAxis axisLeft = mHorizontalBarChart1.getAxisLeft();
        axisLeft.setEnabled(false);
        //设置Y轴数值 从零开始
        axisLeft.setStartAtZero(true);

        mHorizontalBarChart1.getAxisLeft().setDrawGridLines(false);
        //设置动画时间
        mHorizontalBarChart1.animateXY(600, 600);

        mHorizontalBarChart1.getLegend().setEnabled(true);


        // 设置表格标示的位置
        Legend l = mHorizontalBarChart1.getLegend();
        //标示坐落再表格左下方
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
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
    }

    public void getData1(ArrayList<InstallInfo.ChildArea> child_area) {
//        if (ListUtil.isEmpty(child_area)) return;

//        mChild_area = child_area;
        mChild_area1 = new ArrayList<>();
        NewInstallInfo newInstallInfo = new NewInstallInfo("60", "天河");
        NewInstallInfo newInstallInfo1 = new NewInstallInfo("50", "白云");
        NewInstallInfo newInstallInfo2 = new NewInstallInfo("40", "黄埔");
        NewInstallInfo newInstallInfo3 = new NewInstallInfo("54", "越秀");
        NewInstallInfo newInstallInfo4 = new NewInstallInfo("25", "荔湾");
        NewInstallInfo newInstallInfo5 = new NewInstallInfo("33", "番禺");
        NewInstallInfo newInstallInfo6 = new NewInstallInfo("42", "增城");
        NewInstallInfo newInstallInfo7 = new NewInstallInfo("65", "林场");
        NewInstallInfo newInstallInfo8 = new NewInstallInfo("78", "南沙");
        NewInstallInfo newInstallInfo9 = new NewInstallInfo("65", "净水公司");


        mChild_area1.add(newInstallInfo);
        mChild_area1.add(newInstallInfo1);
        mChild_area1.add(newInstallInfo2);
        mChild_area1.add(newInstallInfo3);
        mChild_area1.add(newInstallInfo4);
        mChild_area1.add(newInstallInfo5);
        mChild_area1.add(newInstallInfo6);
        mChild_area1.add(newInstallInfo7);
        mChild_area1.add(newInstallInfo8);
        mChild_area1.add(newInstallInfo9);

        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<BarEntry> values1 = new ArrayList<>();
//        ArrayList<BarEntry> values2 = new ArrayList<>();
//        ArrayList<BarEntry> values3 = new ArrayList<>();
        int i = 0;
        for (NewInstallInfo childArea : mChild_area1) {
            i++;
            BarEntry barEntry = new BarEntry(i, Float.valueOf(childArea.getInstall_percent()));
            BarEntry barEntry1 = new BarEntry(i, Float.valueOf(childArea.getInstall_percent()));
            values.add(barEntry);
            values1.add(barEntry1);
        }
        BarDataSet set1, set2;
        mHorizontalBarChart1.getXAxis().setAxisMaximum(mChild_area1.size());

        if (mHorizontalBarChart1.getData() != null &&
                mHorizontalBarChart1.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mHorizontalBarChart1.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) mHorizontalBarChart1.getData().getDataSetByIndex(1);
            set1.setValues(values);
            set2.setValues(values1);
            mHorizontalBarChart1.getData().notifyDataChanged();
            mHorizontalBarChart1.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "今日签到");
            set2 = new BarDataSet(values1, "昨日签到");
            set1.setColors(getResources().getColor(R.color.agmobile_primary_green));
            set1.setDrawValues(false);
            set2.setColors(getResources().getColor(R.color.agmobile_blue));
            set2.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            dataSets.add(set2);

            int barAmount = dataSets.size(); //需要显示柱状图的类别 数量
            //设置组间距占比30% 每条柱状图宽度占比 70% /barAmount  柱状图间距占比 0%
            float groupSpace = 0.2f; //柱状图组之间的间距
            float barSpace = 0;
            float barWidth = (1f - groupSpace) / barAmount;
            //设置柱状图宽度  (barWidth + barSpace) * barAmount + groupSpace = (0.3 + 0.05) * 2 + 0.3 = 1.00
            BarData data = new BarData(dataSets);
            data.setBarWidth(barWidth);
            mHorizontalBarChart1.setData(data);

            //(起始点、柱状图组间距、柱状图之间间距)
            data.groupBars(0f, groupSpace, barSpace);


            mHorizontalBarChart1.setFitBars(true);

            set1.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "个";
                }
            });
            set2.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "个";
                }
            });

        }


        if (mHorizontalBarChart1.getData() != null) {
            //设置柱形统计图上的值
            mHorizontalBarChart1.getData().setValueTextSize(10);
            for (IDataSet set : mHorizontalBarChart1.getData().getDataSets()) {
                set.setDrawValues(true);
            }
        }

        //绘制图表
        mHorizontalBarChart1.invalidate();

    }

    @Override
    public void showAreaInfo(List<String> areaList) {
        if (!ListUtil.isEmpty(areaList)) {
            Map<String, Object> map = new HashMap<>();
            for (String area : areaList) {
                map.put(area, area);
            }

            mSp_dist.setItemsMap(map);
            mSp_dist.selectItem("全市");

        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoadError(Exception e) {

    }
}
