package com.augurit.agmobile.agwater5.gcjs.gzcn;


import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.view.model.ViewInfo;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.augurit.agmobile.busi.bpm.widget.WidgetType.CHECKBOX_EXPAND;

/**
 * com.augurit.agmobile.agwater5.gcjs.bszn
 * Created by sdb on 2019/4/3  14:38.
 * Desc：告知承诺
 */

public class GzcnActivity extends BaseViewListActivity {

    @Override
    protected void initView() {
        mTitleText = "告知承诺";
        super.initView();
        mView.addViewLoadListener(aVoid -> {

            return null;
        });
    }

    @Override
    protected ArrayList<String> getPageTitles() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("告知承诺制清单管理");
        titles.add("程序性审查");
        return titles;
    }

    @Override
    protected Map<String, ViewInfo> getViewInfos() {
        HashMap<String, ViewInfo> viewInfoHashMap = new HashMap<>();
        ArrayList<String> pageTitles = getPageTitles();
        viewInfoHashMap.put(pageTitles.get(0), getFilterView());
        viewInfoHashMap.put(pageTitles.get(1), getFilterView1());
        return viewInfoHashMap;
    }

    private ViewInfo getFilterView() {

        DictBuilder builder = new DictBuilder();
        builder.item("全部");
        builder.item("济南市环境保护局");
        builder.item("济南市城乡规划局");
        builder.item("济南市交通运输局");
        builder.item("济南市水务局");
        builder.item("济南市住房和城乡建设局");
        builder.item("济南市公路管理局");
        builder.item("济南市自然资源局");
        builder.item("济南市应急管理局");
        ViewInfo viewInfo = new FormBuilder()
                .title("筛选条件")
                .addElement(new ElementBuilder("addr")
                        .widget(new WidgetBuilder(CHECKBOX_EXPAND)
                                .title("部门选择")
                                .maxLimit(1)
                                .defaultSelection(0)
                                .allowCancelCheck(false)
                                .initData(builder.build())
                                .build())
                        .build()).buildAsViewInfo();
        return viewInfo;
    }

    private ViewInfo getFilterView1() {

        DictBuilder builder = new DictBuilder();
        builder.item("全部");
        builder.item("济南市发展和改革局");
        builder.item("济南市公安局");
        builder.item("济南市交通运输局");
        builder.item("济南市水务局");
        builder.item("济南市气象局");
        builder.item("济南市人民防空办公室");
        ViewInfo viewInfo = new FormBuilder()
                .title("筛选条件")
                .addElement(new ElementBuilder("addr")
                        .widget(new WidgetBuilder(CHECKBOX_EXPAND)
                                .title("部门选择")
                                .maxLimit(1)
                                .defaultSelection(0)
                                .allowCancelCheck(false)
                                .initData(builder.build())
                                .build())
                        .build()).buildAsViewInfo();
        return viewInfo;
    }

    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {

        ArrayList<BaseViewListFragment> list = new ArrayList<>();
        list.add(new ProjectCheckFragment());
        list.add(new ListManageFragment());
        return list;
    }
}
