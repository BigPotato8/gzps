package com.augurit.agmobile.agwater5.gcjs.publicaffair;


import android.support.annotation.Nullable;

import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.view.model.ViewInfo;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.augurit.agmobile.busi.bpm.widget.WidgetType.CHECKBOX_EXPAND;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITTEXT;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.TIME_INTERVAL;

/**
 * com.augurit.agmobile.agwater5.gcjs.publicaffair
 * Created by sdb on 2019/4/2  9:23.
 * Desc：
 */

public class GcjsPublicAffairActivity extends BaseViewListActivity {

    private BaseFormWidget mFilterWidget;

    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        ArrayList<BaseViewListFragment> listFragments = new ArrayList<>();
        listFragments.add(new GcjsPublicAffairFragment());
        return listFragments;
    }


    @Override
    protected Map<String, ViewInfo> getViewInfos() {
        HashMap<String, ViewInfo> viewInfoHashMap = new HashMap<>();
        ArrayList<String> pageTitles = getPageTitles();
        viewInfoHashMap.put(pageTitles.get(0), getAffairFilterView());
        return viewInfoHashMap;
    }

    @Override
    protected void initView() {
        mTitleText = "申报流程";
        super.initView();
        mView.addViewLoadListener(aVoid -> {
            mFilterWidget = mView.getFilterWidget("申报流程", "assessTime");

            return null;
        });
    }

    @Override
    protected ArrayList<String> getPageTitles() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("申报流程");
        return titles;
    }


    @Override
    protected Void onFilterWidgetValueChange(String pageTitle, BaseFormWidget widget, Object value, @Nullable Object item, boolean isEffective) {
        if ("addr".equals(widget.getElement().getElementCode()) && "按部门筛选".equals(value)) {
            mFilterWidget.setVisible(true);
        } else if ("addr".equals(widget.getElement().getElementCode()) && "按主题筛选".equals(value)) {
            mFilterWidget.setVisible(false);
        }
        return super.onFilterWidgetValueChange(pageTitle, widget, value, item, isEffective);
    }


    public ViewInfo getAffairFilterView() {
        DictBuilder builder = new DictBuilder();
        builder.item("社会投资项目");
        builder.item("政府投资项目");

        DictBuilder builder1 = new DictBuilder();
        builder1.item("按主题筛选");
        builder1.item("按部门筛选");

        DictBuilder builder2 = new DictBuilder();
        builder2.item("立项用地规划许可");
        builder2.item("工程建设许可");
        builder2.item("施工许可");
        builder2.item("竣工验收");
        ViewInfo viewInfo = new FormBuilder()
                .title("筛选条件")
                .addElement(new ElementBuilder("addr")
                        .widget(new WidgetBuilder(CHECKBOX_EXPAND)
                                .title("按申报类型")
                                .maxLimit(1)
                                .allowCancelCheck(true)
                                .initData(builder1.build())
                                .build())
                        .build())
                .addElement(new ElementBuilder("assessTime")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .hint("输入部门名称关键字")
                                .title("申报部门")
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("createPeople")
                        .widget(new WidgetBuilder(CHECKBOX_EXPAND)
                                .title("投资类型")
                                .maxLimit(1)
                                .allowCancelCheck(true)
                                .initData(builder.build())
                                .build())
                        .build())
                .addElement(new ElementBuilder("createPeople1")
                        .widget(new WidgetBuilder(CHECKBOX_EXPAND)
                                .maxLimit(1)
                                .title("项目类型")
                                .allowCancelCheck(true)
                                .initData(builder2.build())
                                .build())
                        .build())
                .addElement(new ElementBuilder("remark")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("项目名称")
                                .hint("输入项目关键字")
                                .build())
                        .build())
                .addElement(new ElementBuilder("sign")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("立项时间")
                                .build())
                        .build()).buildAsViewInfo();
        return viewInfo;
    }
}
