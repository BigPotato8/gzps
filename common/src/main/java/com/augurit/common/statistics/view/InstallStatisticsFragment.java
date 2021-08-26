package com.augurit.common.statistics.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.view.combineview.AGMultiCheck;
import com.augurit.common.R;
import com.augurit.common.statistics.model.EchartsDataBean;
import com.augurit.common.statistics.model.InstallInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
public class InstallStatisticsFragment extends Fragment implements IStatisticsContract.View {

    private IStatisticsContract.Presenter mPresenter;
    private AGMultiCheck multiCheck;
    private WebView mWebView;
    private TextView tv_all;
    private TextView tv_install;
    private TextView tv_noInstall;
    private RadioButton leadRb;
    private RadioButton patrolRb;
    private RadioGroup mRadioGroup;
    private ArrayList<String> defaultSelectItemList = new ArrayList<>();
    private boolean roleType = false;
    private LinearLayout loading_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.install_statistics, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initWebView();
        initListener();
    }

    private void initView(View view) {
        ((TextView) view.findViewById(R.id.tv_statisticsName)).setText(getString(R.string.statistic_app_title));
        multiCheck = view.findViewById(R.id.area_multcheck);
        mRadioGroup = view.findViewById(R.id.role_rg);
        leadRb = view.findViewById(R.id.lead_rb);
        patrolRb = view.findViewById(R.id.patrol_rb);
        tv_all = view.findViewById(R.id.all_install_count);
        tv_install = view.findViewById(R.id.install_count);
        tv_noInstall = view.findViewById(R.id.no_install_count);
        mWebView = view.findViewById(R.id.chart_webview);
        loading_layout = view.findViewById(R.id.loading_layout);
    }

    private void initListener() {
        multiCheck.setOnItemSelectedListener((value, item, position, selected) -> {
            if (!selected) {
                return;
            }
            if (value.contains("净水")) {
                value = "净水";
            }
            mPresenter.loadInstallInfo(value, roleType);
        });
        mRadioGroup.setOnCheckedChangeListener((radioGroup, childId) -> {
            if (radioGroup.getCheckedRadioButtonId() == R.id.lead_rb) {
                roleType = true;
                changeViewStyle(radioGroup.getCheckedRadioButtonId());
                multiCheck.setValue(defaultSelectItemList.get(0));
                mPresenter.loadAreaInfo(roleType);
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.patrol_rb) {
                roleType = false;
                changeViewStyle(radioGroup.getCheckedRadioButtonId());
                multiCheck.setValue(defaultSelectItemList.get(0));
                mPresenter.loadAreaInfo(roleType);
            }
        });
        tv_install.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AppInstallDetailActivity.class);
            intent.putExtra("org_name", multiCheck.getValue());
            intent.putExtra("roleType", roleType);
            intent.putExtra("inInstall", "true");
            startActivity(intent);
        });
        tv_noInstall.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AppInstallDetailActivity.class);

            intent.putExtra("org_name", multiCheck.getValue());
            intent.putExtra("roleType", roleType);
            intent.putExtra("inInstall", "false");
            startActivity(intent);
        });

    }

    private void changeViewStyle(int checkedId) {
        if (checkedId == R.id.lead_rb) {
            leadRb.setBackgroundColor(getResources().getColor(R.color.agmobile_primary));
            leadRb.setTextColor(Color.WHITE);
            patrolRb.setBackgroundColor(Color.WHITE);
            patrolRb.setTextColor(getResources().getColor(R.color.agmobile_primary));
        } else if (checkedId == R.id.patrol_rb) {
            patrolRb.setBackgroundColor(getResources().getColor(R.color.agmobile_primary));
            patrolRb.setTextColor(Color.WHITE);
            leadRb.setBackgroundColor(Color.WHITE);
            leadRb.setTextColor(getResources().getColor(R.color.agmobile_primary));
        }
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaScriptinterface(), "android");
        mWebView.loadUrl("file:///android_asset/echarts/installChart.html");
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
                mPresenter.loadInstallInfo(multiCheck.getValue(), roleType);
            }
        });
    }

    public class JavaScriptinterface {

        public JavaScriptinterface() {

        }

        /**
         * 点击柱状图柱子进入人员安装详细页面
         */
        @JavascriptInterface
        public void toDetailPage(String org_name) {
            Intent intent = new Intent(getActivity(), AppInstallDetailActivity.class);
            intent.putExtra("org_name", org_name);
            intent.putExtra("roleType", roleType);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new StatisticsPresenter(this);
        mPresenter.loadAreaInfo(roleType);
    }

    @Override
    public void showAreaInfo(List<String> areaList) {
        if (areaList.size() == 0) {
            mPresenter.loadAreaInfo(roleType);
        } else {
            defaultSelectItemList.clear();
            defaultSelectItemList.add(areaList.get(0));
            multiCheck.setItemList(areaList);
            multiCheck.setTitleGone();
            multiCheck.setValue(defaultSelectItemList.get(0));
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
    public void showInstallData(InstallInfo.InstallData installData) {
        if (installData == null) {
            ToastUtil.shortToast(getActivity(), "获取安装率数据失败");
            return;
        }
        tv_all.setText(installData.getTotal() + "");
        tv_install.setText(installData.getInstall() + "");
        tv_noInstall.setText((installData.getTotal() - installData.getInstall()) + "");
        showPieChart(installData);
        if (multiCheck.getValue().equals("全市")) {
            showBarChart(installData.getChild_area());
        }
    }

    private void showPieChart(InstallInfo.InstallData installData) {
        float percent;
        if (installData.getTotal() == 0) {
            percent = 0;
        } else {
            float num = (float) (installData.getInstall() * 100.0 / installData.getTotal());
            DecimalFormat df = new DecimalFormat("#.0");
            String format = df.format(num);
            percent = Float.parseFloat(format);
        }
        String eChartPieJson = EchartsDataBean.getInstance().getEchartsPieJson(percent, multiCheck.getValue());
        mWebView.loadUrl("javascript:createPieChart('pie'," + eChartPieJson + ");");
    }

    private void showBarChart(ArrayList<InstallInfo.ChildArea> installData) {

        float[] floats = new float[installData.size()];
        String[] yAxle = new String[installData.size()];
        for (int i = 0; i < installData.size(); i++) {
            floats[i] = Float.parseFloat(installData.get(i).getInstall_percent());
            String area_name = installData.get(i).getArea_name();
            //因为接口返回的区域名称很长（如天河区水务局） 而我们只需要显示天河区 但是有个别是市水务局 净水公司四个字的 所有这里处理下
            if (area_name.length() == 4) {
                yAxle[i] = installData.get(i).getArea_name();//四个字的直接显示
            } else {
                yAxle[i] = installData.get(i).getArea_name().substring(0, 2);//截取前三个字
            }
        }
        String echartsBarJson = EchartsDataBean.getInstance().getEchartsBarJson(yAxle, floats);
        mWebView.loadUrl("javascript:createBarChart('bar'," + echartsBarJson + ");");
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
