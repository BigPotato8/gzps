package com.augurit.common.statistics.view;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.AGMultiCheck;
import com.augurit.common.R;
import com.augurit.common.statistics.model.EchartsDataBean;
import com.augurit.common.statistics.model.ReportStatisticInfo;
import com.augurit.common.statistics.model.TwoDayReportInfo;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.statistics.view
 * @createTime 创建时间 ：2018-09-05
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class ReportStatisticsFragment extends Fragment implements IStatisticsContract.View {
    private AGMultiCheck area_multiCheck, facility_multiCheck, facility_multiCheck2;
    private WebView mWebView;
    private ArrayList<String> stringList;
    private StatisticsPresenter mPresenter;
    private Button btn_start_date;
    private Button btn_end_date;
    private Calendar cal;
    private Long startDate = null;
    private Long endDate = null;
    private Long TempEndDate = null;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;
    private TextView increase_check_count;
    private TextView correct_check_count;
    private TextView all_check_count;
    private TextView increase_question_count;
    private TextView correct_question_count;
    private TextView all_question_count;
    private TextView all_report_count;
    private TextView increase_report_count;
    private TextView check_report_count;
    private String areaName, facilityName;
    private Button btn_confirm_date;
    private RecyclerView mRecyclerView;
    private TwoDayListAdapter twoDayListAdapter;
    private LinearLayout loading_layout;
    private CompositeDisposable compositeDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_statistics, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new StatisticsPresenter(this);
        mPresenter.loadAreaInfo(false);
        mPresenter.loadFacilityTypeInfo();

        mPresenter.loadTwoDayReportInfo(facility_multiCheck2.getValue());
        twoDayListAdapter = new TwoDayListAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(twoDayListAdapter);
    }

    private void initView(View view) {
        compositeDisposable = new CompositeDisposable();
        loading_layout = view.findViewById(R.id.loading_layout);
        ((TextView) view.findViewById(R.id.tv_statisticsName)).setText(getString(R.string.statistic_report));
        area_multiCheck = view.findViewById(R.id.area_multiCheck);
        facility_multiCheck = view.findViewById(R.id.facility_multiCheck);
        facility_multiCheck2 = view.findViewById(R.id.facility_multiCheck2);
        mWebView = view.findViewById(R.id.chart_webview);

        btn_confirm_date = view.findViewById(R.id.stats_time_ok);

        btn_start_date = view.findViewById(R.id.btn_start_date);
        btn_end_date = view.findViewById(R.id.btn_end_date);
        //数据上报
        all_report_count = view.findViewById(R.id.all_report_count);
        increase_report_count = view.findViewById(R.id.increase_report_count);
        check_report_count = view.findViewById(R.id.check_report_count);
        //审核通过
        increase_check_count = view.findViewById(R.id.increase_check_count);
        correct_check_count = view.findViewById(R.id.correct_check_count);
        all_check_count = view.findViewById(R.id.all_check_count);
        //存在疑问
        increase_question_count = view.findViewById(R.id.increase_question_count);
        correct_question_count = view.findViewById(R.id.correct_question_count);
        all_question_count = view.findViewById(R.id.all_question_count);

        mRecyclerView = view.findViewById(R.id.rv_all_upload);
        initWebView();
        setStateDate();
        setCurrentDate();
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/echarts/ReportBarChart.html");
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mPresenter.loadReportInfo(area_multiCheck.getValue(), facility_multiCheck.getValue(), startDate, endDate);

            }
        });
    }

    private void initListener() {
        btn_start_date.setOnClickListener(view -> {
            if (startDate == null) {
                showDatePickerDialog(btn_start_date, cal, START_DATE);
            } else {
                cal.setTimeInMillis(startDate);
                showDatePickerDialog(btn_start_date, cal, START_DATE);
            }
        });

        btn_end_date.setOnClickListener(view -> {
            if (TempEndDate == null) {
                showDatePickerDialog(btn_end_date, cal, END_DATE);
            } else {
                cal.setTimeInMillis(TempEndDate);
                showDatePickerDialog(btn_end_date, cal, END_DATE);
            }
        });
        area_multiCheck.setOnItemSelectedListener((value, item, position, selected) -> {
            if (!selected) {
                return;
            }
            checkDate();
            areaName = value;
            mPresenter.loadReportInfo(areaName, facility_multiCheck.getValue(), startDate, endDate);
        });
        facility_multiCheck.setOnItemSelectedListener((value, item, position, selected) -> {
            if (!selected) {
                return;
            }
            checkDate();
            facilityName = value;
            mPresenter.loadReportInfo(area_multiCheck.getValue(), facilityName, startDate, endDate);
        });
        facility_multiCheck2.setOnItemSelectedListener((value, item, position, selected) -> {
            if (!selected) {
                return;
            }
            mPresenter.loadTwoDayReportInfo(value);
        });
        Disposable disposable = RxView.clicks(btn_confirm_date)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    if (startDate > TempEndDate) {
                        ToastUtil.shortToast(getActivity(), "开始时间不能比结束时间大");
                        return;
                    }
                    mPresenter.loadReportInfo(area_multiCheck.getValue(), facility_multiCheck.getValue(), startDate, endDate);
                });
        compositeDisposable.add(disposable);

    }

    private void checkDate() {
        if (startDate > TempEndDate) {
            ToastUtil.shortToast(getActivity(), "开始时间不能比结束时间大");
            return;
        }
    }

    private void showDatePickerDialog(Button btn, Calendar calendar, int type) {
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
        btn_end_date.setText(year + "-" + month + "-" + day);
        endDate = new Date(year - 1900, month - 1, day + 1).getTime();
        TempEndDate = new Date(year - 1900, month - 1, day).getTime();
    }

    private void setStateDate() {
        cal = Calendar.getInstance();
        startDate = new Date(2018 - 1900, 0, 1).getTime();
        btn_start_date.setText(2018 + "-" + 1 + "-" + 1);
    }

    @Override
    public void showAreaInfo(List<String> areaList) {
        if (areaList.size() == 0) {
            mPresenter.loadAreaInfo(false);
        } else {
            stringList = new ArrayList<>();
            ArrayList<String> defaultSelectItemList = new ArrayList<>();
            stringList.addAll(areaList);
            defaultSelectItemList.add(stringList.get(0));
            area_multiCheck.setItemList(areaList);
            area_multiCheck.setTitleGone();
            area_multiCheck.setSelectedItemList(defaultSelectItemList);
            area_multiCheck.setMaxLimit(1);
        }

    }

    @Override
    public void showLoading() {
        loading_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading_layout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadError(Exception e) {
        ToastUtil.shortToast(getActivity(), e.getMessage());
    }

    @Override
    public void showFacilityType(List<String> facilityList) {
        if (facilityList.size() == 0) {
            mPresenter.loadFacilityTypeInfo();
        } else {
            ArrayList<String> defaultSelectItemList = new ArrayList<>();
            defaultSelectItemList.add(facilityList.get(0));
            facility_multiCheck.setItemList(facilityList);
            facility_multiCheck2.setItemList(facilityList);
            facility_multiCheck.setTitleGone();
            facility_multiCheck2.setTitleGone();
            facility_multiCheck.setSelectedItemList(defaultSelectItemList);
            facility_multiCheck2.setSelectedItemList(defaultSelectItemList);
            facility_multiCheck.setMaxLimit(1);
            facility_multiCheck2.setMaxLimit(1);
        }

    }

    @Override
    public void showReportData(ReportStatisticInfo reportStatisticInfo) {
        if (reportStatisticInfo == null) {
            ToastUtil.shortToast(getActivity(), "获取问题上报数据失败！");
            return;
        }
        all_report_count.setText(reportStatisticInfo.getTotalStr());
        increase_report_count.setText(reportStatisticInfo.getCorrCountStr());
        check_report_count.setText(reportStatisticInfo.getLackCountStr());
        all_check_count.setText(reportStatisticInfo.getPassTotalStr());
        correct_check_count.setText(reportStatisticInfo.getPassCorrCountStr());
        increase_check_count.setText(reportStatisticInfo.getPassLackCountStr());
        all_question_count.setText(reportStatisticInfo.getDoubtTotalStr());
        correct_question_count.setText(reportStatisticInfo.getDoubtCorrCountStr());
        increase_question_count.setText(reportStatisticInfo.getDoubtLackCountStr());
        if (area_multiCheck.getValue().equals("全市")) {
            createBarChart(reportStatisticInfo.getCharts());
        }

    }

    @Override
    public void showTwoDayReportData(TwoDayReportInfo twoDayReportInfo) {
        if (twoDayReportInfo == null || !twoDayReportInfo.getSuccess()) {
            ToastUtil.shortToast(getActivity(), "获取昨天和今天上报数据失败");
            return;
        }
        showListResult(twoDayReportInfo);
    }

    private void showListResult(TwoDayReportInfo twoDayReportInfo) {
        List<TwoDayReportInfo.ToDayEntity> toDay = twoDayReportInfo.getToDay();
        List<TwoDayReportInfo.YestDayEntity> yestDay = twoDayReportInfo.getYestDay();

        List<TwoDayReportInfo.ToDayEntity> newToday = new ArrayList<>();
        List<TwoDayReportInfo.YestDayEntity> newyestDay = new ArrayList<>();
        int sumYCorrect = 0;
        int sumYLack = 0;

        int sumTCorrect = 0;
        int sumTLack = 0;
        for (int i = 0; i < toDay.size(); i++) {
            TwoDayReportInfo.ToDayEntity tempToday = new TwoDayReportInfo.ToDayEntity();
            TwoDayReportInfo.YestDayEntity tempYestDay = new TwoDayReportInfo.YestDayEntity();
            String orgName = toDay.get(i).getName();
            tempToday.setName(orgName);
            tempYestDay.setName(orgName);
            if (ListUtil.isEmpty(toDay)) {
                tempToday.setCorrCount(0);
                tempToday.setLackCount(0);
            } else {
                for (TwoDayReportInfo.ToDayEntity childOrg : toDay) {
                    if (childOrg.getName().contains(orgName) || (orgName.equals("净水公司") && childOrg.getName().contains("净水"))) {
                        tempToday.setCorrCount(childOrg.getCorrCount());
                        tempToday.setLackCount(childOrg.getLackCount());
                        sumTCorrect += childOrg.getCorrCount();
                        sumTLack += childOrg.getLackCount();
                        break;
                    } else {
                        tempToday.setCorrCount(0);
                        tempToday.setLackCount(0);
                    }
                }
            }

            if (ListUtil.isEmpty(yestDay)) {
                tempYestDay.setCorrCount(0);
                tempYestDay.setLackCount(0);
            } else {
                for (TwoDayReportInfo.YestDayEntity childOrg : yestDay) {
                    if (childOrg.getName().contains(orgName) || (orgName.equals("净水公司") && childOrg.getName().contains("净水"))) {
                        tempYestDay.setCorrCount(childOrg.getCorrCount());
                        tempYestDay.setLackCount(childOrg.getLackCount());
                        sumYCorrect += childOrg.getCorrCount();
                        sumYLack += childOrg.getLackCount();
                        break;
                    } else {
                        tempYestDay.setCorrCount(0);
                        tempYestDay.setLackCount(0);
                    }
                }
            }
            if (orgName.equals("总计")) {
                tempToday.setLackCount(sumTLack);
                tempToday.setCorrCount(sumTCorrect);
                tempYestDay.setLackCount(sumYLack);
                tempYestDay.setCorrCount(sumYCorrect);
            }
            newToday.add(tempToday);
            newyestDay.add(tempYestDay);
        }
        twoDayListAdapter.refresh(newyestDay, newToday);
    }

    private void createBarChart(List<ReportStatisticInfo.ChartsEntity> charts) {
        if (charts == null) {
            return;
        }
        float[] floats = new float[charts.size()];
        String[] yAxle = new String[charts.size()];
        for (int i = 0; i < yAxle.length; i++) {
            floats[i] = charts.get(i).getTotal();
            String area_name = charts.get(i).getName();
            //因为接口返回的区域名称很长（如天河区水务局）
            if (area_name.contains("净水")) {
                yAxle[i] = "净水公司";
            } else {
                yAxle[i] = charts.get(i).getName().substring(0, 2);//大于四个字的截取前三个字
            }
        }
        String eChartsBarJson = EchartsDataBean.getInstance().getEchartsBarJson(yAxle, floats);
        mWebView.loadUrl("javascript:createBarChart('bar'," + eChartsBarJson + ");");
    }

    /**
     * 设置日期
     */
    public void display(Button dateDisplay, int year,
                        int monthOfYear, int dayOfMonth) {
        dateDisplay.setText(new StringBuffer().append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(compositeDisposable!=null){
            compositeDisposable.clear();
            compositeDisposable=null;
        }
        if(mPresenter!=null){
            mPresenter.destroyDisposable();
        }
    }
}
