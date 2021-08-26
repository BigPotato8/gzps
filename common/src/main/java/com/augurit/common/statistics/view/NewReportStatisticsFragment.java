package com.augurit.common.statistics.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.spinner.AMSpinner;
import com.augurit.agmobile.common.view.videopicker.utils.ScreenUtils;
import com.augurit.common.R;
import com.augurit.common.statistics.model.InstallInfo;
import com.augurit.common.statistics.model.NewInstallInfo;
import com.augurit.common.statistics.model.ReportTableBean;
import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class NewReportStatisticsFragment extends Fragment implements IStatisticsContract.View {

    private HorizontalBarChart mHorizontalBarChart, mHorizontalBarChart1, mHorizontalBarChart2;
    private StatisticsPresenter mPresenter;
    private SmartTable mTable;
    private AMSpinner sp_dist, sp_lev;
    private TextView start_time, end_time;
    private ArrayList<NewInstallInfo> mChild_area, mChild_area1, mChild_area2;
    private Calendar cal;
    private Long startDate, endDate, TempEndDate;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.new_report_statistics, null);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new StatisticsPresenter(this);
        mPresenter.loadAreaInfo(false);
        mPresenter.loadFacilityTypeInfo();
    }

    private void initView(View view) {
        mTable = view.findViewById(R.id.table);
        sp_dist = view.findViewById(R.id.sp_dist);
        View tv_value = sp_dist.findViewById(R.id.tv_value);
        if (tv_value instanceof TextView) {
            ((TextView) tv_value).setTextSize(ScreenUtils.dp2px(getActivity(), 5));
        }
        sp_lev = view.findViewById(R.id.sp_lev);
        View tv_value1 = sp_lev.findViewById(R.id.tv_value);
        if (tv_value1 instanceof TextView) {
            ((TextView) tv_value1).setTextSize(ScreenUtils.dp2px(getActivity(), 5));
        }
        start_time = view.findViewById(R.id.start_time);
        start_time.setTextSize(ScreenUtils.dp2px(getActivity(), 5));
        end_time = view.findViewById(R.id.end_time);
        end_time.setTextSize(ScreenUtils.dp2px(getActivity(), 5));
        mHorizontalBarChart = view.findViewById(R.id.hor_view);
        mHorizontalBarChart1 = view.findViewById(R.id.hor_report_view);
        mHorizontalBarChart2 = view.findViewById(R.id.hor_check_view);

        setStateDate();
        setCurrentDate();

        initTableView();
        initMPAndroidChart();
        initMPAndroidChart1();
        initMPAndroidChart2();

        getData(null);
        getData1(null);
        getData2(null);

        sp_dist.setOnItemClickListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object value) {

            }
        });
        sp_lev.setOnItemClickListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
            }
        });

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startDate == null) {
                    showDatePickerDialog(start_time, cal, START_DATE);
                } else {
                    cal.setTimeInMillis(startDate);
                    showDatePickerDialog(start_time, cal, START_DATE);
                }
            }
        });

        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TempEndDate == null) {
                    showDatePickerDialog(end_time, cal, END_DATE);
                } else {
                    cal.setTimeInMillis(TempEndDate);
                    showDatePickerDialog(end_time, cal, END_DATE);
                }
            }
        });
    }

    private void checkDate() {
        if (startDate > TempEndDate) {
            ToastUtil.shortToast(getActivity(), "开始时间不能比结束时间大");
            return;
        }
    }

    private void showDatePickerDialog(TextView btn, Calendar calendar, int type) {
        new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    display(btn, year, monthOfYear, dayOfMonth);
                    if (type == START_DATE) {
                        startDate = new Date(year - 1900, monthOfYear, dayOfMonth).getTime();
                    } else {
                        endDate = new Date(year - 1900, monthOfYear, dayOfMonth + 1).getTime();
                        TempEndDate = new Date(year - 1900, monthOfYear, dayOfMonth).getTime();
                    }
                }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setCurrentDate() {
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);       //获取年月日时分秒
        int month = cal.get(Calendar.MONTH) + 1;   //获取到的月份是从0开始计数
        int day = cal.get(Calendar.DAY_OF_MONTH);
        end_time.setText(year + "-" + month + "-" + day);
        endDate = new Date(year - 1900, month - 1, day + 1).getTime();
        TempEndDate = new Date(year - 1900, month - 1, day).getTime();
    }

    private void setStateDate() {
        cal = Calendar.getInstance();
        startDate = new Date(2018 - 1900, 0, 1).getTime();
        start_time.setText(2018 + "-" + 1 + "-" + 1);
    }

    /**
     * 设置日期
     */
    public void display(TextView dateDisplay, int year,
                        int monthOfYear, int dayOfMonth) {
        dateDisplay.setText(new StringBuffer().append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth));
    }

    private void initMPAndroidChart() {
        mHorizontalBarChart.getDescription().setEnabled(false);

        //设置最大值条目，超出之后不会有值
        mHorizontalBarChart.setMaxVisibleValueCount(1000000);

        //分别在x轴和y轴上进行缩放
        mHorizontalBarChart.setPinchZoom(true);
        //设置剩余统计图的阴影
        mHorizontalBarChart.setDrawBarShadow(false);
        //设置网格布局
        mHorizontalBarChart.setDrawGridBackground(false);

        mHorizontalBarChart.setPinchZoom(false);
        mHorizontalBarChart.setDoubleTapToZoomEnabled(false);

        mHorizontalBarChart.setExtraBottomOffset(10);

        //获取x轴线
        XAxis xAxis = mHorizontalBarChart.getXAxis();

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
                if (!ListUtil.isEmpty(mChild_area)) {
                    return mChild_area.get((int) value).getArea_name();
                }
                return "";
            }
        });
        //缩放后x 轴数据重叠问题
        xAxis.setGranularityEnabled(true);
        xAxis.setTextSize(16f);

        //获取右边y标签
        YAxis axisRight = mHorizontalBarChart.getAxisRight();
        axisRight.setStartAtZero(true);
        axisRight.setTextSize(16f);
        //获取左边y轴的标签
        YAxis axisLeft = mHorizontalBarChart.getAxisLeft();
        axisLeft.setEnabled(false);
        //设置Y轴数值 从零开始
        axisLeft.setStartAtZero(true);

        mHorizontalBarChart.getAxisLeft().setDrawGridLines(false);
        //设置动画时间
        mHorizontalBarChart.animateXY(600, 600);

        mHorizontalBarChart.getLegend().setEnabled(true);


        // 设置表格标示的位置
        Legend l = mHorizontalBarChart.getLegend();
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

    public void getData(ArrayList<InstallInfo.ChildArea> child_area) {
//        if (ListUtil.isEmpty(child_area)) return;

//        mChild_area = child_area;
        mChild_area = new ArrayList<>();
        NewInstallInfo newInstallInfo = new NewInstallInfo("500", "天河");
        NewInstallInfo newInstallInfo1 = new NewInstallInfo("355", "白云");
        NewInstallInfo newInstallInfo2 = new NewInstallInfo("432", "黄埔");
        NewInstallInfo newInstallInfo3 = new NewInstallInfo("356", "越秀");
        NewInstallInfo newInstallInfo4 = new NewInstallInfo("144", "荔湾");
        NewInstallInfo newInstallInfo5 = new NewInstallInfo("232", "番禺");
        NewInstallInfo newInstallInfo6 = new NewInstallInfo("365", "增城");
        NewInstallInfo newInstallInfo7 = new NewInstallInfo("444", "林场");
        NewInstallInfo newInstallInfo8 = new NewInstallInfo("511", "南沙");
        NewInstallInfo newInstallInfo9 = new NewInstallInfo("677", "净水公司");
        mChild_area.add(newInstallInfo);
        mChild_area.add(newInstallInfo1);
        mChild_area.add(newInstallInfo2);
        mChild_area.add(newInstallInfo3);
        mChild_area.add(newInstallInfo4);
        mChild_area.add(newInstallInfo5);
        mChild_area.add(newInstallInfo6);
        mChild_area.add(newInstallInfo7);
        mChild_area.add(newInstallInfo8);
        mChild_area.add(newInstallInfo9);

        ArrayList<BarEntry> values = new ArrayList<>();
//        BarEntry barEntry = new BarEntry(Float.valueOf("0"), Float.valueOf("100"));
//        BarEntry barEntry1 = new BarEntry(Float.valueOf("1"), Float.valueOf("210"));
//        BarEntry barEntry2 = new BarEntry(Float.valueOf("2"), Float.valueOf("300"));
//        BarEntry barEntry3 = new BarEntry(Float.valueOf("3"), Float.valueOf("450"));
//        BarEntry barEntry4 = new BarEntry(Float.valueOf("4"), Float.valueOf("300"));
//        BarEntry barEntry5 = new BarEntry(Float.valueOf("5"), Float.valueOf("650"));
//        BarEntry barEntry6 = new BarEntry(Float.valueOf("6"), Float.valueOf("740"));
//        values.add(barEntry);
//        values.add(barEntry1);
//        values.add(barEntry2);
//        values.add(barEntry3);
//        values.add(barEntry4);
//        values.add(barEntry5);
//        values.add(barEntry6);
        int i = 0;
        for (NewInstallInfo childArea : mChild_area) {
            BarEntry barEntry = new BarEntry(i++, Float.valueOf(childArea.getInstall_percent()));
            values.add(barEntry);
        }
        BarDataSet set1;

        if (mHorizontalBarChart.getData() != null &&
                mHorizontalBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mHorizontalBarChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mHorizontalBarChart.getData().notifyDataChanged();
            mHorizontalBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "上报数(个)");
            set1.setColors(getResources().getColor(R.color.agmobile_blue));
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            mHorizontalBarChart.setData(data);

            mHorizontalBarChart.setFitBars(true);

            set1.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "个";
                }
            });

        }


        if (mHorizontalBarChart.getData() != null) {
            //设置柱形统计图上的值
            mHorizontalBarChart.getData().setValueTextSize(12);
            for (IDataSet set : mHorizontalBarChart.getData().getDataSets()) {
                set.setDrawValues(true);
            }
        }

        //绘制图表
        mHorizontalBarChart.invalidate();

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
        NewInstallInfo newInstallInfo = new NewInstallInfo("500", "天河");
        NewInstallInfo newInstallInfo1 = new NewInstallInfo("355", "白云");
        NewInstallInfo newInstallInfo2 = new NewInstallInfo("432", "黄埔");
        NewInstallInfo newInstallInfo3 = new NewInstallInfo("356", "越秀");
        NewInstallInfo newInstallInfo4 = new NewInstallInfo("144", "荔湾");
        NewInstallInfo newInstallInfo5 = new NewInstallInfo("232", "番禺");
        NewInstallInfo newInstallInfo6 = new NewInstallInfo("365", "增城");
        NewInstallInfo newInstallInfo7 = new NewInstallInfo("444", "林场");
        NewInstallInfo newInstallInfo8 = new NewInstallInfo("511", "南沙");
        NewInstallInfo newInstallInfo9 = new NewInstallInfo("677", "净水公司");


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
            set1 = new BarDataSet(values, "今日新增");
            set2 = new BarDataSet(values1, "昨日新增");
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

    private void initMPAndroidChart2() {
        mHorizontalBarChart2.getDescription().setEnabled(false);

        //设置最大值条目，超出之后不会有值
        mHorizontalBarChart2.setMaxVisibleValueCount(Integer.MAX_VALUE);

        //分别在x轴和y轴上进行缩放
        mHorizontalBarChart2.setPinchZoom(true);
        //设置剩余统计图的阴影
        mHorizontalBarChart2.setDrawBarShadow(false);
        //设置网格布局
        mHorizontalBarChart2.setDrawGridBackground(false);

        mHorizontalBarChart2.setPinchZoom(false);
        mHorizontalBarChart2.setDoubleTapToZoomEnabled(false);

        mHorizontalBarChart2.setExtraBottomOffset(10);

        //获取x轴线
        XAxis xAxis = mHorizontalBarChart2.getXAxis();

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
                if (!ListUtil.isEmpty(mChild_area2)) {
                    return mChild_area2.get((int) Math.abs(value) % mChild_area2.size()).getArea_name();
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
        YAxis axisRight = mHorizontalBarChart2.getAxisRight();
        axisRight.setStartAtZero(true);
        axisRight.setTextSize(16f);
        //获取左边y轴的标签
        YAxis axisLeft = mHorizontalBarChart2.getAxisLeft();
        axisLeft.setEnabled(false);
        //设置Y轴数值 从零开始
        axisLeft.setStartAtZero(true);

        mHorizontalBarChart2.getAxisLeft().setDrawGridLines(false);
        //设置动画时间
        mHorizontalBarChart2.animateXY(600, 600);

        mHorizontalBarChart2.getLegend().setEnabled(true);


        // 设置表格标示的位置
        Legend l = mHorizontalBarChart2.getLegend();
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

    public void getData2(ArrayList<InstallInfo.ChildArea> child_area) {
//        if (ListUtil.isEmpty(child_area)) return;

//        mChild_area = child_area;
        mChild_area2 = new ArrayList<>();
        NewInstallInfo newInstallInfo = new NewInstallInfo("500", "天河");
        NewInstallInfo newInstallInfo1 = new NewInstallInfo("355", "白云");
        NewInstallInfo newInstallInfo2 = new NewInstallInfo("432", "黄埔");
        NewInstallInfo newInstallInfo3 = new NewInstallInfo("356", "越秀");
        NewInstallInfo newInstallInfo4 = new NewInstallInfo("144", "荔湾");
        NewInstallInfo newInstallInfo5 = new NewInstallInfo("232", "番禺");
        NewInstallInfo newInstallInfo6 = new NewInstallInfo("365", "增城");
        NewInstallInfo newInstallInfo7 = new NewInstallInfo("444", "林场");
        NewInstallInfo newInstallInfo8 = new NewInstallInfo("511", "南沙");
        NewInstallInfo newInstallInfo9 = new NewInstallInfo("677", "净水公司");


        mChild_area2.add(newInstallInfo);
        mChild_area2.add(newInstallInfo1);
        mChild_area2.add(newInstallInfo2);
        mChild_area2.add(newInstallInfo3);
        mChild_area2.add(newInstallInfo4);
        mChild_area2.add(newInstallInfo5);
        mChild_area2.add(newInstallInfo6);
        mChild_area2.add(newInstallInfo7);
        mChild_area2.add(newInstallInfo8);
        mChild_area2.add(newInstallInfo9);

        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<BarEntry> values1 = new ArrayList<>();
//        ArrayList<BarEntry> values2 = new ArrayList<>();
//        ArrayList<BarEntry> values3 = new ArrayList<>();
        int i = 0;
        for (NewInstallInfo childArea : mChild_area2) {
            i++;
            BarEntry barEntry = new BarEntry(i, Float.valueOf(childArea.getInstall_percent()));
            BarEntry barEntry1 = new BarEntry(i, Float.valueOf(childArea.getInstall_percent()));
            BarEntry barEntry2 = new BarEntry(i, Float.valueOf(childArea.getInstall_percent()));
            BarEntry barEntry3 = new BarEntry(i, Float.valueOf(childArea.getInstall_percent()));
            values.add(barEntry);
            values1.add(barEntry1);
//            values2.add(barEntry2);
//            values3.add(barEntry3);
        }
        BarDataSet set1, set2;
        mHorizontalBarChart2.getXAxis().setAxisMaximum(mChild_area2.size());

        if (mHorizontalBarChart2.getData() != null &&
                mHorizontalBarChart2.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mHorizontalBarChart2.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) mHorizontalBarChart2.getData().getDataSetByIndex(1);
            set1.setValues(values);
            set2.setValues(values1);
            mHorizontalBarChart2.getData().notifyDataChanged();
            mHorizontalBarChart2.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "今日校核");
            set2 = new BarDataSet(values1, "昨日校核");
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
            mHorizontalBarChart2.setData(data);

            //(起始点、柱状图组间距、柱状图之间间距)
            data.groupBars(0f, groupSpace, barSpace);


            mHorizontalBarChart2.setFitBars(true);

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


        if (mHorizontalBarChart2.getData() != null) {
            //设置柱形统计图上的值
            mHorizontalBarChart2.getData().setValueTextSize(10);
            for (IDataSet set : mHorizontalBarChart2.getData().getDataSets()) {
                set.setDrawValues(true);
            }
        }

        //绘制图表
        mHorizontalBarChart2.invalidate();

    }

    @Override
    public void showFacilityType(List<String> facilityList) {
        if (!ListUtil.isEmpty(facilityList)) {
            Map<String, Object> map = new HashMap<>();
            for (String area : facilityList) {
                map.put(area, area);
            }

            sp_lev.setItemsMap(map);
            sp_lev.selectItem("窨井");
        }
    }

    @Override
    public void showAreaInfo(List<String> areaList) {
        if (!ListUtil.isEmpty(areaList)) {
            Map<String, Object> map = new HashMap<>();
            for (String area : areaList) {
                map.put(area, area);
            }

            sp_dist.setItemsMap(map);
            sp_dist.selectItem("全市");

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

    private void initTableView() {

        Column<String> firstColumn = new Column<>("", "rowName");
        Column<Integer> allNum = new Column<>("总数", "allNum");
        Column<Integer> newNUm = new Column<>("新增", "newNum");
        Column<Integer> checkNum = new Column<>("校核", "checkNum");
        List<Column> listColumn = new ArrayList<>();
        listColumn.add(firstColumn);
        listColumn.add(allNum);
        listColumn.add(newNUm);
        listColumn.add(checkNum);

        List<ReportTableBean> list = new ArrayList<>();
        ReportTableBean reportTableBean = new ReportTableBean("数据上报", 123, 455, 433);
        ReportTableBean reportTableBean1 = new ReportTableBean("审核通过", 123, 455, 433);
        ReportTableBean reportTableBean2 = new ReportTableBean("存在疑问", 123, 455, 433);
        list.add(reportTableBean);
        list.add(reportTableBean1);
        list.add(reportTableBean2);
        TableData<ReportTableBean> tableData = new TableData<>("", list, listColumn);

        TableConfig config = mTable.getConfig();
        config.setShowXSequence(false);
        config.setShowYSequence(false);
        config.setMinTableWidth(ScreenUtils.getScreenWidth(getActivity()));
        config.setShowTableTitle(false);
        config.setColumnTitleStyle(new FontStyle(ScreenUtils.sp2px(getActivity(), 18), getResources().getColor(R.color.black)));
        config.setContentStyle(new FontStyle(ScreenUtils.sp2px(getActivity(), 16), R.color.agmobile_text_body));
//        config.setYSequenceCellBgFormat(backgroundFormat2);
        config.setContentCellBackgroundFormat(backgroundFormat1);
        config.setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.agmobile_grey_0)));
        mTable.setTableData(tableData);
    }


    //设置每行 标题字体和背景
    ICellBackgroundFormat<Integer> backgroundFormat2 = new BaseCellBackgroundFormat<Integer>() {
        @Override
        public int getBackGroundColor(Integer position) {
            if (position == 0) {
                return getResources().getColor(R.color.agmobile_grey_0);
            }
            return TableConfig.INVALID_COLOR;
//            cellInfo.column.setDrawFormat(new TextDrawFormat() {
//                @Override
//                public void setTextPaint(TableConfig config, CellInfo cellInfo, Paint paint) {
//                    super.setTextPaint(config, cellInfo, paint);
//                    paint.setTextSize(18);
//                }
//            });
        }

        @Override
        public int getTextColor(Integer position) {
            if (position == 0) {
                return getResources().getColor(R.color.black);
            }
            return TableConfig.INVALID_COLOR;
        }
    };

    //设置列颜色
    ICellBackgroundFormat<CellInfo> backgroundFormat1 = new BaseCellBackgroundFormat<CellInfo>() {

        @Override
        public int getTextColor(CellInfo cellInfo) {
            if (cellInfo.col == 0) {

                if ("".equals(cellInfo.column.getColumnName())) {
                    return getResources().getColor(R.color.black);
                } else {
                    return getResources().getColor(R.color.agmobile_text_body);
                }
            } else {
                return getResources().getColor(R.color.agmobile_text_body);
            }
        }

        @Override
        public int getBackGroundColor(CellInfo cellInfo) {
            if (cellInfo.col == 0) {
                return getResources().getColor(R.color.agmobile_grey_0);
            }
            return TableConfig.INVALID_COLOR;

        }
    };


}
