package com.augurit.common.statistics.view;

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
import com.augurit.common.R;
import com.augurit.common.statistics.model.InstallInfo;
import com.augurit.common.statistics.model.NewInstallInfo;
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

import java.text.DecimalFormat;
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
public class NewInstallStatisticsFragment extends Fragment implements IStatisticsContract.View {

    private TextView tv_all, tv_install, tv_noInstall;
    private ClockView clock_view;
    private HorizontalBarChart mHorizontalBarChart;
    private StatisticsPresenter mPresenter;
    private AMSpinner sp_dist, sp_lev;
    private boolean roleType;
//    private ArrayList<InstallInfo.ChildArea> mChild_area;
    private ArrayList<NewInstallInfo> mChild_area;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.new_install_statistics, null);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new StatisticsPresenter(this);

        Map<String, Object> map = new HashMap<>();
        map.put("一线人员统计", "一线人员统计");
        map.put("管理层统计", "管理层统计");

        sp_lev.setItemsMap(map);
        sp_lev.selectItem("一线人员统计");

        mPresenter.loadAreaInfo(roleType);

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

            mPresenter.loadInstallInfo(sp_dist.getText(), roleType);
        }
    }

    @Override
    public void showLoading() {
        //TODO 临时
        getData(null);
    }

    @Override
    public void hideLoading() {
        mHorizontalBarChart.setNoDataText("加载数据失败");
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

        float percent;
        if (installData.getTotal() == 0) {
            percent = 0;
        } else {
            float num = (float) (installData.getInstall() * 100.0 / installData.getTotal());
            DecimalFormat df = new DecimalFormat("#.0");
            String format = df.format(num);
            percent = Float.parseFloat(format);
        }

        clock_view.setCompleteDegree(percent);
        getData(installData.getChild_area());
    }

    private void initView(View view) {
        sp_dist = view.findViewById(R.id.sp_dist);
        sp_lev = view.findViewById(R.id.sp_lev);
        tv_all = view.findViewById(R.id.all_install_count);
        tv_install = view.findViewById(R.id.install_count);
        tv_noInstall = view.findViewById(R.id.no_install_count);
        clock_view = view.findViewById(R.id.clock_view);
        mHorizontalBarChart = view.findViewById(R.id.hor_view);

        clock_view.setCompleteDegree(0f);
        mHorizontalBarChart.setNoDataText("正在加载数据");
        initMPAndroidChart();

//        sp_dist.setMaxLimit(1);
//        sp_dist.setAllowCancelCheck(false);
//        sp_lev.setMaxLimit(1);
//        sp_lev.setAllowCancelCheck(false);
        sp_dist.setOnItemClickListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object value) {
                clock_view.setTitle(value + "安装率统计");
                clock_view.setCompleteDegree(0f);

                if (((String) value).contains("净水")) {
                    value = "净水";
                }
                mPresenter.loadInstallInfo(((String) value), roleType);
            }
        });
        sp_lev.setOnItemClickListener(new AMSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                roleType = position == 0;
                mPresenter.loadAreaInfo(roleType);
            }
        });
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
        NewInstallInfo newInstallInfo = new NewInstallInfo("500","天河");
        NewInstallInfo newInstallInfo1 = new NewInstallInfo("355","白云");
        NewInstallInfo newInstallInfo2 = new NewInstallInfo("432","黄埔");
        NewInstallInfo newInstallInfo3 = new NewInstallInfo("356","越秀");
        NewInstallInfo newInstallInfo4 = new NewInstallInfo("144","荔湾");
        NewInstallInfo newInstallInfo5 = new NewInstallInfo("232","番禺");
        NewInstallInfo newInstallInfo6 = new NewInstallInfo("365","增城");
        NewInstallInfo newInstallInfo7 = new NewInstallInfo("444","林场");
        NewInstallInfo newInstallInfo8 = new NewInstallInfo("511","南沙");
        NewInstallInfo newInstallInfo9 = new NewInstallInfo("677","净水公司");
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
        for (NewInstallInfo  childArea: mChild_area) {
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
            set1 = new BarDataSet(values, "安装数(个)");
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

}



