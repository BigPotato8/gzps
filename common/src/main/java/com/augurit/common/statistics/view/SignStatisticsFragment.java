package com.augurit.common.statistics.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.common.lib.json.JsonUtil;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.AGMultiCheck;
import com.augurit.common.R;
import com.augurit.common.common.util.StringUtil;
import com.augurit.common.statistics.model.SignEchartsBarBean;
import com.augurit.common.statistics.model.SignEchartsPieBean;
import com.augurit.common.statistics.model.SignStatisticInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.statistics.view
 * @createTime 创建时间 ：2018-09-05
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class SignStatisticsFragment extends Fragment implements IStatisticsContract.View {
    private AGMultiCheck multiCheck;
    private ArrayList<String> stringList;
    private StatisticsPresenter mPresenter;
    private TextView tv_all_sign;
    private TextView tv_today_sign;
    private TextView tv_yesterday_sign;
    private int year;
    private int month;
    private int day;
    private WebView mWebView;
    private SignEchartsBarBean mSignEchartsBarBean;
    private LinearLayout loading_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_statistics, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        loading_layout = view.findViewById(R.id.loading_layout);
        ((TextView) view.findViewById(R.id.tv_statisticsName)).setText(getString(R.string.statistic_sign));
        multiCheck = view.findViewById(R.id.area_multcheck);
        tv_all_sign = view.findViewById(R.id.all_sign_count);
        tv_today_sign = view.findViewById(R.id.today_sign_count);
        tv_yesterday_sign = view.findViewById(R.id.yesterday_sign_count);
        mWebView = view.findViewById(R.id.chart_webview);
        initWebView();
        initListener();
        initTime();
    }

    private void initListener() {
        multiCheck.setOnItemSelectedListener((value, item, position, selected) -> {
            if (!selected) {
                return;
            }
            String areaName = multiCheck.getValue();
            mPresenter.loadSignInfo(areaName);
        });
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/echarts/signChart.html");
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
                mPresenter.loadSignInfo(multiCheck.getValue());
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new StatisticsPresenter(this);
        mPresenter.loadAreaInfo(false);
    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
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
            multiCheck.setItemList(areaList);
            multiCheck.setTitleGone();
            multiCheck.setSelectedItemList(defaultSelectItemList);
            multiCheck.setMaxLimit(1);
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
    }

    @Override
    public void showSignData(List<SignStatisticInfo> signListData) {
        if (signListData == null || signListData.size() == 0 || signListData.get(0).getSignDate() == null) {
            ToastUtil.shortToast(getActivity(), "获取签到数据失败！");
            return;
        }
        showCountView(signListData);
        showPieChart(signListData);
        showBarChart(signListData);
    }

    private void showBarChart(List<SignStatisticInfo> signListData) {
        if (mSignEchartsBarBean == null) {
            mSignEchartsBarBean = new SignEchartsBarBean();
        }
        List<Double> yesData = new ArrayList<>();
        List<Double> todData = new ArrayList<>();
        List<String> distrc = new ArrayList<>();
        String monthStr = "" + month;
        String dayStr = "" + day;
        if (month < 10) {
            monthStr = "0" + monthStr;
        }
        if (day < 10) {
            dayStr = "0" + dayStr;
        }
        String time = year + "" + monthStr + "" + dayStr;
        for (SignStatisticInfo signStatisticInfoBean : signListData) {
            //全市、今天
            if (signStatisticInfoBean.getOrgName().equals("全市") && signStatisticInfoBean.getSignDate().equals(time)) {
                for (int i = 0; i < stringList.size(); i++) {
                    String orgName = stringList.get(i);
                    if (ListUtil.isEmpty(signStatisticInfoBean.getChildOrgs())) {
                        todData.add(0.00);
                        continue;
                    }
                    for (SignStatisticInfo.ChildOrgsEntity childOrgsEntity : signStatisticInfoBean.getChildOrgs()) {
                        if (childOrgsEntity.getOrgName().equals(orgName)) {
                            distrc.add(orgName);
                            todData.add(childOrgsEntity.getSignPercentage());
                            break;
                        }
                    }
                }
            } else if (signStatisticInfoBean.getOrgName().equals("全市") && !signStatisticInfoBean.getSignDate().equals(time)) {
                for (int i = 0; i < stringList.size(); i++) {
                    String orgName = stringList.get(i);
                    if (ListUtil.isEmpty(signStatisticInfoBean.getChildOrgs())) {
                        yesData.add(0.00);
                        continue;
                    }
                    for (SignStatisticInfo.ChildOrgsEntity childOrgsEntity : signStatisticInfoBean.getChildOrgs()) {
                        if (childOrgsEntity.getOrgName().equals(orgName)) {
                            yesData.add(childOrgsEntity.getSignPercentage());
                            break;
                        }
                    }
                }
            } else if (!signStatisticInfoBean.getOrgName().equals("全市") && signStatisticInfoBean.getSignDate().equals(time)) {
                for (int i = 0; i < stringList.size(); i++) {
                    String orgName = stringList.get(i);
                    if (signStatisticInfoBean.getOrgName().equals(orgName)) {
                        distrc.add(signStatisticInfoBean.getOrgName());
                        todData.add(signStatisticInfoBean.getSignPercentage());
                    } else {
                        distrc.add(orgName);
                        todData.add(0.0);
                    }
                }
            } else if (!signStatisticInfoBean.getOrgName().equals("全市") && !signStatisticInfoBean.getSignDate().equals(time)) {
                for (int i = 0; i < stringList.size(); i++) {
                    String orgName = stringList.get(i);
                    if (signStatisticInfoBean.getOrgName().equals(orgName)) {
                        yesData.add(signStatisticInfoBean.getSignPercentage());
                    } else {
                        yesData.add(0.0);
                    }
                }
            }
        }
        Collections.reverse(distrc);
        Collections.reverse(todData);
        Collections.reverse(yesData);
        mSignEchartsBarBean.setTimes(distrc);
        List<String> todData1 = new ArrayList<>();
        List<String> yesData1 = new ArrayList<>();
        for (Double t : todData) {
            todData1.add(StringUtil.valueOf(t, 2));
        }
        for (Double y : yesData) {
            yesData1.add(StringUtil.valueOf(y, 2));
        }
        mSignEchartsBarBean.setTodaydata(todData1);
        mSignEchartsBarBean.setYesterdaydata(yesData1);
        String json = JsonUtil.getJson(mSignEchartsBarBean);
        mWebView.loadUrl("javascript:createBarChart('bar'," + json + ");");
    }

    private void showPieChart(List<SignStatisticInfo> signListData) {
        SignEchartsPieBean mSignEchartsPieBean = new SignEchartsPieBean();
        int total = 0;
        int yesterday_sign = 0;
        int yesterday_not_sign = 0;
        double yesterday_percent = 0.0;
        int today_sign = 0;
        int today_not_sign = 0;
        double today_percent = 0.0;
        String monthStr = "" + month;
        String dayStr = "" + day;
        if (month < 10) {
            monthStr = "0" + monthStr;
        }
        if (day < 10) {
            dayStr = "0" + dayStr;
        }
        String time = year + "" + monthStr + "" + dayStr;
        mSignEchartsPieBean.setTitle("签到率统计");
        for (SignStatisticInfo signStatisticInfoBean : signListData) {
            if (time.equals(signStatisticInfoBean.getSignDate())) {
                total += signStatisticInfoBean.getTotal();
                today_sign += signStatisticInfoBean.getSignNumber();
                today_percent += signStatisticInfoBean.getSignPercentage();
            } else {
                yesterday_sign += signStatisticInfoBean.getSignNumber();
                yesterday_percent += signStatisticInfoBean.getSignPercentage();
            }
        }
        yesterday_not_sign = total - yesterday_sign;
        today_not_sign = total - today_sign;
        //模拟数据
        List<String> legends = new ArrayList<>();
        List<SignEchartsPieBean.ValueData> valueDatas = new ArrayList<>();
        SignEchartsPieBean.ValueData valueData = new SignEchartsPieBean.ValueData();
        valueData.setName("总人数:" + total + "\n" + "签到数:" + today_sign);
        valueData.setValue(today_not_sign);
        valueDatas.add(valueData);
        SignEchartsPieBean.ValueData valueData2 = new SignEchartsPieBean.ValueData();
        valueData2.setName("今日" + StringUtil.valueOf(today_percent, 2) + "%");
        valueData2.setValue(today_sign);
        valueDatas.add(valueData2);
        mSignEchartsPieBean.setValues(valueDatas);
        legends.add("今日" + StringUtil.valueOf(today_percent, 2) + "%");
        SignEchartsPieBean mSignEchartsPieBean2 = new SignEchartsPieBean();
        //模拟数据
        mSignEchartsPieBean2.setTitle("签到率统计");
        List<SignEchartsPieBean.ValueData> zvalueDatas = new ArrayList<>();
        SignEchartsPieBean.ValueData zvalueData = new SignEchartsPieBean.ValueData();
        zvalueData.setName("总人数:" + total + "\n" + " 签到数:" + yesterday_sign + " ");
        zvalueData.setValue(yesterday_not_sign);
        zvalueDatas.add(zvalueData);
        SignEchartsPieBean.ValueData zvalueData2 = new SignEchartsPieBean.ValueData();
        zvalueData2.setName("昨日" + StringUtil.valueOf(yesterday_percent, 2) + "%");
        zvalueData2.setValue(yesterday_sign);
        zvalueDatas.add(zvalueData2);
        mSignEchartsPieBean2.setValues(zvalueDatas);
        legends.add("昨日" + StringUtil.valueOf(yesterday_percent, 2) + "%");
        mSignEchartsPieBean.setLegends(legends);
        String json1 = JsonUtil.getJson(mSignEchartsPieBean);
        String json2 = JsonUtil.getJson(mSignEchartsPieBean2);
        mWebView.loadUrl("javascript:createPieChart('pie'," + json1 + "," + json2 + ");");
    }

    private void showCountView(List<SignStatisticInfo> signListData) {
        tv_all_sign.setText(signListData.get(0).getTotal() + "");
        tv_today_sign.setText(signListData.get(0).getSignNumber() + "");
        tv_yesterday_sign.setText(signListData.get(1).getSignNumber() + "");
    }
}
