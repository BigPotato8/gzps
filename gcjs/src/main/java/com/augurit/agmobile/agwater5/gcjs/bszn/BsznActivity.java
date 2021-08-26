package com.augurit.agmobile.agwater5.gcjs.bszn;


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
 * Desc：办事指南
 */

public class BsznActivity extends BaseViewListActivity {

    @Override
    protected void initView() {
        mTitleText = "办事指南";
        super.initView();
        mView.addViewLoadListener(aVoid -> {

            return null;
        });
    }

    @Override
    protected ArrayList<String> getPageTitles() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("按主题");
        titles.add("按部门");
        return titles;
    }

    @Override
    protected Map<String, ViewInfo> getViewInfos() {
        HashMap<String, ViewInfo> viewInfoHashMap = new HashMap<>();
        ArrayList<String> pageTitles = getPageTitles();
        viewInfoHashMap.put(pageTitles.get(0), getFilterView1());
        viewInfoHashMap.put(pageTitles.get(1), getFilterView2());
        return viewInfoHashMap;
    }

    private ViewInfo getFilterView1() {
        DictBuilder builder = new DictBuilder();
        builder.item("企业投资类");
        builder.item("政府投资类");
        builder.item("重大项目预审批");
        builder.item("其他并联事项");
        builder.item("投资项目多评合一");

        DictBuilder builder2 = new DictBuilder();
        builder2.item("立项用地规划许可");
        builder2.item("工程建设许可");
        builder2.item("施工许可");
        builder2.item("竣工验收");
        ViewInfo viewInfo = new FormBuilder()
                .title("筛选条件")
                .addElement(new ElementBuilder("addr")
                        .widget(new WidgetBuilder(CHECKBOX_EXPAND)
                                .title("主题")
                                .maxLimit(1)
                                .defaultSelection(0)
                                .allowCancelCheck(false)
                                .initData(builder.build())
                                .build())
                        .build())
                .addElement(new ElementBuilder("createPeople")
                        .widget(new WidgetBuilder(CHECKBOX_EXPAND)
                                .title("事项类型")
                                .maxLimit(1)
                                .allowCancelCheck(false)
                                .defaultSelection(0)
                                .initData(builder2.build())
                                .build())
                        .build()).buildAsViewInfo();
        return viewInfo;
    }


    private ViewInfo getFilterView2() {

        DictBuilder builder = new DictBuilder();
        builder.item("全部");
        builder.item("济南市质量技术监督局");
        builder.item("济南市海洋与渔业局");
        builder.item("济南市安全生产监督管理局");
        builder.item("济南市公路管理局");
        builder.item("济南市城市综合管理局");
        builder.item("济南市文化广电旅游体育局");
        builder.item("济南市自然资源局");
        builder.item("济南市气象局");
        builder.item("济南市环境保护局");
        builder.item("济南市民族宗教事务局");
        builder.item("济南市住房和城乡建设局");
        builder.item("济南市林业局");
        builder.item("济南市公安局");
        builder.item("济南市国家安全局");
        builder.item("济南市公安消防局");
        builder.item("济南市水务局");
        builder.item("济南市国家安全局");
        builder.item("济南市交通运输局");
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
        list.add(new BaseTopicFragment());
        list.add(new BaseDepartFragment());
        return list;
    }
}
