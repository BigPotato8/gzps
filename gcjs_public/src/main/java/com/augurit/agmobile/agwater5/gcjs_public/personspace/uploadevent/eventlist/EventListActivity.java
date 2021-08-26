package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.eventlist;

import android.content.Intent;

import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.OrgAndThemeBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source.ReportRepository;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.view.model.ViewInfo;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.common.lib.common.Function;
import com.augurit.agmobile.common.view.combineview.AGMultiSpinner;
import com.augurit.agmobile.common.view.combineview.IDictItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.augurit.agmobile.busi.bpm.widget.WidgetProperty.PROPERTY_HINT;
import static com.augurit.agmobile.busi.bpm.widget.WidgetProperty.PROPERTY_MAX_LIMIT;
import static com.augurit.agmobile.busi.bpm.widget.WidgetProperty.PROPERTY_TITLE;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITTEXT;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.SPINNER_EXPAND;

/**
 * 单项申报-事项选择界面
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent
 * Created by sdb on 2019/4/19  9:55.
 * Desc：
 */

public class EventListActivity extends BaseViewListActivity {
    EventListFragment mEventListFragment;
    ReportRepository mRepository;

    @Override
    protected void initView() {
        mTitleText = "事项列表";
        super.initView();
        mRepository = new ReportRepository();
        mView.addViewLoadListener(new Function<Void, Void>() {
            @Override
            public Void apply(Void aVoid) {
                //todo 获取网络列表项
//                BaseFormWidget department = mView.getFilterWidget(mTitleText, "department");
//                ((AGMultiSpinner)department.getView()).initData();
                mRepository.getDepartmentList()
                        .subscribe(listApiResult -> {
                            OrgAndThemeBean data = listApiResult.getData();
                            List<IDictItem> items = new ArrayList<>();
                            for (OrgAndThemeBean.OpuOmOrgsBean bean : data.getOpuOmOrgs()) {
                                DictItem item = new DictItem();
                                item.setValue(bean.getOrgId());
                                item.setLabel(bean.getOrgName());
                                items.add(item);
                            }
                            BaseFormWidget department = mView.getFilterWidget(getPageTitles().get(0), "orgId");
                            ((AGMultiSpinner) department.getView()).initData(items);
                        }, throwable -> throwable.printStackTrace());
                return null;
            }
        });
    }


    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        ArrayList<BaseViewListFragment> list = new ArrayList<>();
        mEventListFragment = new EventListFragment();
        list.add(mEventListFragment);
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mEventListFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected Map<String, ViewInfo> getViewInfos() {
        HashMap<String, ViewInfo> viewInfoHashMap = new HashMap<>();
        ArrayList<String> pageTitles = getPageTitles();
        viewInfoHashMap.put(pageTitles.get(0), getDTViewInfo());
        return viewInfoHashMap;
    }

    private ViewInfo getDTViewInfo() {
        ViewInfo viewInfo = new FormBuilder()
                .addElement(new ElementBuilder("orgId")
                        .widget(new WidgetBuilder(SPINNER_EXPAND)
                                .addProperty(PROPERTY_TITLE, "部门")
                                .addProperty(PROPERTY_MAX_LIMIT, "1")
                                .build())
                        .build())
//                .addElement(new ElementBuilder("theme")
//                        .widget(new WidgetBuilder(SPINNER_EXPAND)
//                                .addProperty(PROPERTY_TITLE, "主题")
//                                .addProperty(PROPERTY_MAX_LIMIT, "1")
//                                .build())
//                        .build())
                .addElement(new ElementBuilder("itemName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .addProperty(PROPERTY_TITLE, "事项名称")
                                .addProperty(PROPERTY_MAX_LIMIT, "200")
                                .addProperty(PROPERTY_HINT, "请输入事项名称")
                                .build())
                        .build())
                .buildAsViewInfo();
        return viewInfo;
    }

}
