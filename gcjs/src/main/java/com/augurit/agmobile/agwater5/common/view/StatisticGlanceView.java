package com.augurit.agmobile.agwater5.common.view;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;

import java.util.ArrayList;

/**
 * 统计概览文字View
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.common.view
 * @createTime 创建时间 ：2018/8/21
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/21
 * @modifyMemo 修改备注：
 */
public class StatisticGlanceView extends RelativeLayout {

    protected TextView tvTitle;
    protected ViewGroup content0;
    protected TextView tvKey0;
    protected TextView tvValue0;
    protected ViewGroup content1;
    protected TextView tvKey1;
    protected TextView tvValue1;
    protected ViewGroup content2;
    protected TextView tvKey2;
    protected TextView tvValue2;
    protected ViewGroup content3;
    protected TextView tvKey3;
    protected TextView tvValue3;

    protected ArrayList<ViewGroup> containers;
    protected ArrayList<TextView> keyTextViews;
    protected ArrayList<TextView> valueTextViews;

    public StatisticGlanceView(Context context) {
        super(context);
        initView();
    }

    public StatisticGlanceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StatisticGlanceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.common_view_statistic_glance, this);
        tvTitle = findViewById(R.id.tv_title);
        content0 = findViewById(R.id.ll_content_0);
        tvKey0 = findViewById(R.id.tv_key_0);
        tvValue0 = findViewById(R.id.tv_value_0);
        content1 = findViewById(R.id.ll_content_1);
        tvKey1 = findViewById(R.id.tv_key_1);
        tvValue1 = findViewById(R.id.tv_value_1);
        content2 = findViewById(R.id.ll_content_2);
        tvKey2 = findViewById(R.id.tv_key_2);
        tvValue2 = findViewById(R.id.tv_value_2);
        content3 = findViewById(R.id.ll_content_3);
        tvKey3 = findViewById(R.id.tv_key_3);
        tvValue3 = findViewById(R.id.tv_value_3);

        containers = new ArrayList<>();
        keyTextViews = new ArrayList<>();
        valueTextViews = new ArrayList<>();

        containers.add(content0);
        containers.add(content1);
        containers.add(content2);
        containers.add(content3);
        keyTextViews.add(tvKey0);
        keyTextViews.add(tvKey1);
        keyTextViews.add(tvKey2);
        keyTextViews.add(tvKey3);
        valueTextViews.add(tvValue0);
        valueTextViews.add(tvValue1);
        valueTextViews.add(tvValue2);
        valueTextViews.add(tvValue3);
    }

    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    public void setValues(ArrayMap<CharSequence, CharSequence> valuesMap) {
        for (int i = 0; i < valuesMap.size(); i++) {
            keyTextViews.get(i).setText(valuesMap.keyAt(i));
            valueTextViews.get(i).setText(valuesMap.valueAt(i));
        }
    }

}
