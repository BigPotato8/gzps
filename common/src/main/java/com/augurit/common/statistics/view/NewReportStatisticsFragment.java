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
 * @author ????????? ???taoerxiang
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.agwater5.statistics.view
 * @createTime ???????????? ???2018-09-05
 * @modifyBy ????????? ???
 * @modifyTime ???????????? ???
 * @modifyMemo ???????????????
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
            ToastUtil.shortToast(getActivity(), "????????????????????????????????????");
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
                // ??????????????????
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setCurrentDate() {
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);       //????????????????????????
        int month = cal.get(Calendar.MONTH) + 1;   //????????????????????????0????????????
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
     * ????????????
     */
    public void display(TextView dateDisplay, int year,
                        int monthOfYear, int dayOfMonth) {
        dateDisplay.setText(new StringBuffer().append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth));
    }

    private void initMPAndroidChart() {
        mHorizontalBarChart.getDescription().setEnabled(false);

        //????????????????????????????????????????????????
        mHorizontalBarChart.setMaxVisibleValueCount(1000000);

        //?????????x??????y??????????????????
        mHorizontalBarChart.setPinchZoom(true);
        //??????????????????????????????
        mHorizontalBarChart.setDrawBarShadow(false);
        //??????????????????
        mHorizontalBarChart.setDrawGridBackground(false);

        mHorizontalBarChart.setPinchZoom(false);
        mHorizontalBarChart.setDoubleTapToZoomEnabled(false);

        mHorizontalBarChart.setExtraBottomOffset(10);

        //??????x??????
        XAxis xAxis = mHorizontalBarChart.getXAxis();

        //??????x??????????????????
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //??????????????????
        xAxis.setDrawGridLines(true);
        //???????????????????????????????????????????????????????????????????????????????????????
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
        //?????????x ?????????????????????
        xAxis.setGranularityEnabled(true);
        xAxis.setTextSize(16f);

        //????????????y??????
        YAxis axisRight = mHorizontalBarChart.getAxisRight();
        axisRight.setStartAtZero(true);
        axisRight.setTextSize(16f);
        //????????????y????????????
        YAxis axisLeft = mHorizontalBarChart.getAxisLeft();
        axisLeft.setEnabled(false);
        //??????Y????????? ????????????
        axisLeft.setStartAtZero(true);

        mHorizontalBarChart.getAxisLeft().setDrawGridLines(false);
        //??????????????????
        mHorizontalBarChart.animateXY(600, 600);

        mHorizontalBarChart.getLegend().setEnabled(true);


        // ???????????????????????????
        Legend l = mHorizontalBarChart.getLegend();
        //??????????????????????????????
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        //??????????????????
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //??????????????????
        l.setDrawInside(false);
        //??????
        l.setForm(Legend.LegendForm.SQUARE);
        //??????
        l.setFormSize(9f);
        //??????
        l.setTextSize(11f);
    }

    public void getData(ArrayList<InstallInfo.ChildArea> child_area) {
//        if (ListUtil.isEmpty(child_area)) return;

//        mChild_area = child_area;
        mChild_area = new ArrayList<>();
        NewInstallInfo newInstallInfo = new NewInstallInfo("500", "??????");
        NewInstallInfo newInstallInfo1 = new NewInstallInfo("355", "??????");
        NewInstallInfo newInstallInfo2 = new NewInstallInfo("432", "??????");
        NewInstallInfo newInstallInfo3 = new NewInstallInfo("356", "??????");
        NewInstallInfo newInstallInfo4 = new NewInstallInfo("144", "??????");
        NewInstallInfo newInstallInfo5 = new NewInstallInfo("232", "??????");
        NewInstallInfo newInstallInfo6 = new NewInstallInfo("365", "??????");
        NewInstallInfo newInstallInfo7 = new NewInstallInfo("444", "??????");
        NewInstallInfo newInstallInfo8 = new NewInstallInfo("511", "??????");
        NewInstallInfo newInstallInfo9 = new NewInstallInfo("677", "????????????");
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
            set1 = new BarDataSet(values, "?????????(???)");
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
                    return (int) value + "???";
                }
            });

        }


        if (mHorizontalBarChart.getData() != null) {
            //??????????????????????????????
            mHorizontalBarChart.getData().setValueTextSize(12);
            for (IDataSet set : mHorizontalBarChart.getData().getDataSets()) {
                set.setDrawValues(true);
            }
        }

        //????????????
        mHorizontalBarChart.invalidate();

    }

    private void initMPAndroidChart1() {
        mHorizontalBarChart1.getDescription().setEnabled(false);

        //????????????????????????????????????????????????
        mHorizontalBarChart1.setMaxVisibleValueCount(Integer.MAX_VALUE);

        //?????????x??????y??????????????????
        mHorizontalBarChart1.setPinchZoom(true);
        //??????????????????????????????
        mHorizontalBarChart1.setDrawBarShadow(false);
        //??????????????????
        mHorizontalBarChart1.setDrawGridBackground(false);

        mHorizontalBarChart1.setPinchZoom(false);
        mHorizontalBarChart1.setDoubleTapToZoomEnabled(false);

        mHorizontalBarChart1.setExtraBottomOffset(10);

        //??????x??????
        XAxis xAxis = mHorizontalBarChart1.getXAxis();

        //??????x??????????????????
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //??????????????????
        xAxis.setDrawGridLines(true);
        //???????????????????????????????????????????????????????????????????????????????????????
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
        //?????????x ?????????????????????
        xAxis.setGranularityEnabled(true);
        xAxis.setTextSize(16f);

        xAxis.setAxisMinimum(0f);
//        xAxis.setAxisMaximum(xValues.size());
        xAxis.setCenterAxisLabels(true);

        //????????????y??????
        YAxis axisRight = mHorizontalBarChart1.getAxisRight();
        axisRight.setStartAtZero(true);
        axisRight.setTextSize(16f);
        //????????????y????????????
        YAxis axisLeft = mHorizontalBarChart1.getAxisLeft();
        axisLeft.setEnabled(false);
        //??????Y????????? ????????????
        axisLeft.setStartAtZero(true);

        mHorizontalBarChart1.getAxisLeft().setDrawGridLines(false);
        //??????????????????
        mHorizontalBarChart1.animateXY(600, 600);

        mHorizontalBarChart1.getLegend().setEnabled(true);


        // ???????????????????????????
        Legend l = mHorizontalBarChart1.getLegend();
        //??????????????????????????????
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        //??????????????????
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //??????????????????
        l.setDrawInside(false);
        //??????
        l.setForm(Legend.LegendForm.SQUARE);
        //??????
        l.setFormSize(9f);
        //??????
        l.setTextSize(11f);
    }

    public void getData1(ArrayList<InstallInfo.ChildArea> child_area) {
//        if (ListUtil.isEmpty(child_area)) return;

//        mChild_area = child_area;
        mChild_area1 = new ArrayList<>();
        NewInstallInfo newInstallInfo = new NewInstallInfo("500", "??????");
        NewInstallInfo newInstallInfo1 = new NewInstallInfo("355", "??????");
        NewInstallInfo newInstallInfo2 = new NewInstallInfo("432", "??????");
        NewInstallInfo newInstallInfo3 = new NewInstallInfo("356", "??????");
        NewInstallInfo newInstallInfo4 = new NewInstallInfo("144", "??????");
        NewInstallInfo newInstallInfo5 = new NewInstallInfo("232", "??????");
        NewInstallInfo newInstallInfo6 = new NewInstallInfo("365", "??????");
        NewInstallInfo newInstallInfo7 = new NewInstallInfo("444", "??????");
        NewInstallInfo newInstallInfo8 = new NewInstallInfo("511", "??????");
        NewInstallInfo newInstallInfo9 = new NewInstallInfo("677", "????????????");


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
            set1 = new BarDataSet(values, "????????????");
            set2 = new BarDataSet(values1, "????????????");
            set1.setColors(getResources().getColor(R.color.agmobile_primary_green));
            set1.setDrawValues(false);
            set2.setColors(getResources().getColor(R.color.agmobile_blue));
            set2.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            dataSets.add(set2);

            int barAmount = dataSets.size(); //?????????????????????????????? ??????
            //?????????????????????30% ??????????????????????????? 70% /barAmount  ????????????????????? 0%
            float groupSpace = 0.2f; //???????????????????????????
            float barSpace = 0;
            float barWidth = (1f - groupSpace) / barAmount;
            //?????????????????????  (barWidth + barSpace) * barAmount + groupSpace = (0.3 + 0.05) * 2 + 0.3 = 1.00
            BarData data = new BarData(dataSets);
            data.setBarWidth(barWidth);
            mHorizontalBarChart1.setData(data);

            //(??????????????????????????????????????????????????????)
            data.groupBars(0f, groupSpace, barSpace);


            mHorizontalBarChart1.setFitBars(true);

            set1.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "???";
                }
            });
            set2.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "???";
                }
            });

        }


        if (mHorizontalBarChart1.getData() != null) {
            //??????????????????????????????
            mHorizontalBarChart1.getData().setValueTextSize(10);
            for (IDataSet set : mHorizontalBarChart1.getData().getDataSets()) {
                set.setDrawValues(true);
            }
        }

        //????????????
        mHorizontalBarChart1.invalidate();

    }

    private void initMPAndroidChart2() {
        mHorizontalBarChart2.getDescription().setEnabled(false);

        //????????????????????????????????????????????????
        mHorizontalBarChart2.setMaxVisibleValueCount(Integer.MAX_VALUE);

        //?????????x??????y??????????????????
        mHorizontalBarChart2.setPinchZoom(true);
        //??????????????????????????????
        mHorizontalBarChart2.setDrawBarShadow(false);
        //??????????????????
        mHorizontalBarChart2.setDrawGridBackground(false);

        mHorizontalBarChart2.setPinchZoom(false);
        mHorizontalBarChart2.setDoubleTapToZoomEnabled(false);

        mHorizontalBarChart2.setExtraBottomOffset(10);

        //??????x??????
        XAxis xAxis = mHorizontalBarChart2.getXAxis();

        //??????x??????????????????
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //??????????????????
        xAxis.setDrawGridLines(true);
        //???????????????????????????????????????????????????????????????????????????????????????
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
        //?????????x ?????????????????????
        xAxis.setGranularityEnabled(true);
        xAxis.setTextSize(16f);

        xAxis.setAxisMinimum(0f);
//        xAxis.setAxisMaximum(xValues.size());
        xAxis.setCenterAxisLabels(true);

        //????????????y??????
        YAxis axisRight = mHorizontalBarChart2.getAxisRight();
        axisRight.setStartAtZero(true);
        axisRight.setTextSize(16f);
        //????????????y????????????
        YAxis axisLeft = mHorizontalBarChart2.getAxisLeft();
        axisLeft.setEnabled(false);
        //??????Y????????? ????????????
        axisLeft.setStartAtZero(true);

        mHorizontalBarChart2.getAxisLeft().setDrawGridLines(false);
        //??????????????????
        mHorizontalBarChart2.animateXY(600, 600);

        mHorizontalBarChart2.getLegend().setEnabled(true);


        // ???????????????????????????
        Legend l = mHorizontalBarChart2.getLegend();
        //??????????????????????????????
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        //??????????????????
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //??????????????????
        l.setDrawInside(false);
        //??????
        l.setForm(Legend.LegendForm.SQUARE);
        //??????
        l.setFormSize(9f);
        //??????
        l.setTextSize(11f);
    }

    public void getData2(ArrayList<InstallInfo.ChildArea> child_area) {
//        if (ListUtil.isEmpty(child_area)) return;

//        mChild_area = child_area;
        mChild_area2 = new ArrayList<>();
        NewInstallInfo newInstallInfo = new NewInstallInfo("500", "??????");
        NewInstallInfo newInstallInfo1 = new NewInstallInfo("355", "??????");
        NewInstallInfo newInstallInfo2 = new NewInstallInfo("432", "??????");
        NewInstallInfo newInstallInfo3 = new NewInstallInfo("356", "??????");
        NewInstallInfo newInstallInfo4 = new NewInstallInfo("144", "??????");
        NewInstallInfo newInstallInfo5 = new NewInstallInfo("232", "??????");
        NewInstallInfo newInstallInfo6 = new NewInstallInfo("365", "??????");
        NewInstallInfo newInstallInfo7 = new NewInstallInfo("444", "??????");
        NewInstallInfo newInstallInfo8 = new NewInstallInfo("511", "??????");
        NewInstallInfo newInstallInfo9 = new NewInstallInfo("677", "????????????");


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
            set1 = new BarDataSet(values, "????????????");
            set2 = new BarDataSet(values1, "????????????");
            set1.setColors(getResources().getColor(R.color.agmobile_primary_green));
            set1.setDrawValues(false);
            set2.setColors(getResources().getColor(R.color.agmobile_blue));
            set2.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            dataSets.add(set2);

            int barAmount = dataSets.size(); //?????????????????????????????? ??????
            //?????????????????????30% ??????????????????????????? 70% /barAmount  ????????????????????? 0%
            float groupSpace = 0.2f; //???????????????????????????
            float barSpace = 0;
            float barWidth = (1f - groupSpace) / barAmount;
            //?????????????????????  (barWidth + barSpace) * barAmount + groupSpace = (0.3 + 0.05) * 2 + 0.3 = 1.00
            BarData data = new BarData(dataSets);
            data.setBarWidth(barWidth);
            mHorizontalBarChart2.setData(data);

            //(??????????????????????????????????????????????????????)
            data.groupBars(0f, groupSpace, barSpace);


            mHorizontalBarChart2.setFitBars(true);

            set1.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "???";
                }
            });
            set2.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "???";
                }
            });

        }


        if (mHorizontalBarChart2.getData() != null) {
            //??????????????????????????????
            mHorizontalBarChart2.getData().setValueTextSize(10);
            for (IDataSet set : mHorizontalBarChart2.getData().getDataSets()) {
                set.setDrawValues(true);
            }
        }

        //????????????
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
            sp_lev.selectItem("??????");
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
            sp_dist.selectItem("??????");

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
        Column<Integer> allNum = new Column<>("??????", "allNum");
        Column<Integer> newNUm = new Column<>("??????", "newNum");
        Column<Integer> checkNum = new Column<>("??????", "checkNum");
        List<Column> listColumn = new ArrayList<>();
        listColumn.add(firstColumn);
        listColumn.add(allNum);
        listColumn.add(newNUm);
        listColumn.add(checkNum);

        List<ReportTableBean> list = new ArrayList<>();
        ReportTableBean reportTableBean = new ReportTableBean("????????????", 123, 455, 433);
        ReportTableBean reportTableBean1 = new ReportTableBean("????????????", 123, 455, 433);
        ReportTableBean reportTableBean2 = new ReportTableBean("????????????", 123, 455, 433);
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


    //???????????? ?????????????????????
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

    //???????????????
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
