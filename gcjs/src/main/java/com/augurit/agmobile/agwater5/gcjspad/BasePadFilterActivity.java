package com.augurit.agmobile.agwater5.gcjspad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.dict.GcjsDictRepository;
import com.augurit.agmobile.agwater5.gcjspad.listener.PadFilterWidgetListener;
import com.augurit.agmobile.busi.bpm.dict.IDictRepository;
import com.augurit.agmobile.busi.bpm.dict.model.Dict;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.form.model.Element;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.view.model.ViewInfo;
import com.augurit.agmobile.busi.bpm.widget.IWidgetFactory;
import com.augurit.agmobile.busi.bpm.widget.WidgetType;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.common.lib.validate.StringUtil;
import com.augurit.agmobile.common.view.combineview.AGTextInterval;
import com.augurit.agmobile.common.view.combineview.AGTimeInterval;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.agmobile.common.view.skin.SkinManager;
import com.augurit.common.common.form.AwWidgetFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.disposables.CompositeDisposable;

import static com.augurit.agmobile.busi.bpm.widget.WidgetProperty.PROPERTY_DICT_ARR;
import static com.augurit.agmobile.busi.bpm.widget.WidgetProperty.PROPERTY_DICT_TYPE_CODE;
import static com.augurit.agmobile.busi.bpm.widget.WidgetProperty.PROPERTY_INIT_DATA;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad
 * @createTime 创建时间 ：2020/12/3
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public abstract class BasePadFilterActivity extends AppCompatActivity {
    protected ViewGroup drawer_container;//筛选界面容器
    protected DrawerLayout drawer_layout;//抽屉layout
    protected ImageView ivClose;
    protected HashMap<String, View> mFilterViewMap = new HashMap<>();     // 每页的筛选View Map
    protected IWidgetFactory mWidgetFactory = new AwWidgetFactory();
    protected IDictRepository mDictRepository = new GcjsDictRepository();
    protected HashMap<String, LinkedHashMap<String, BaseFormWidget>> mWidgetsMapMap = new HashMap<>();    // 每页的筛选控件Map
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //初始化筛选界面
        initFilterView();
    }

    protected void initFilterView(){
        drawer_layout = findViewById(R.id.drawer_layout_pad);
        drawer_container = findViewById(R.id.drawer_container_pad);
        if(drawer_layout==null || drawer_container==null){
            return;
        }
        Map<String, ViewInfo> viewInfos = getMainViewInfos();
        initFilter(viewInfos, "待办任务");
    }

    protected abstract Map<String, ViewInfo> getMainViewInfos();

    protected abstract int getLayoutId();

    protected void initFilter(Map<String, ViewInfo> filterInfoMap, String title){
        for (Map.Entry<String, ViewInfo> entry : filterInfoMap.entrySet()) {
            initFilterWidget(entry.getKey(), entry.getValue());
        }
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                GravityCompat.END);
        drawer_layout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.END);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                        GravityCompat.END);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        showFilterView(title);//默认显示pageKey为1的界面

    }

    protected void initFilterWidget(String pageKey, ViewInfo info) {
        if (info == null) {
            mWidgetsMapMap.put(pageKey, null);
            return;
        }
        View view = SkinManager.getInstance().inflate(this, R.layout.view_filter_pad, drawer_container, false);
        ViewGroup view_container = view.findViewById(R.id.view_container);
        drawer_container.addView(view);

        mFilterViewMap.put(pageKey, view);
        LinkedHashMap<String, BaseFormWidget> widgetsMap = new LinkedHashMap<>();
        mWidgetsMapMap.put(pageKey, widgetsMap);

        PadFilterWidgetListener widgetListener = new PadFilterWidgetListener(pageKey, widgetsMap);
        widgetListener.setPadFilterWidgetValueChangeListener((_pageKey, widget, value, item, isEffective) -> {
//            if (mFilterWidgetValueChangeListener != null) {
//                mFilterWidgetValueChangeListener.apply(s, baseFormWidget, o, o2, aBoolean);
//            }

            onFilterValueChange(_pageKey, widget, value, item, isEffective);


            return null;
        });

        for (Element element : info.getElements()) {
            BaseFormWidget widget = mWidgetFactory.create(this, element, true);
            if (widget == null) {
                continue;
            }
            widget.setFormState(FormState.STATE_EDIT);
            widget.setWidgetListener(widgetListener);
            if (element.getWidget().getWidgetCode().equals(WidgetType.DIVIDER_GROUP)) {
                element.setElementCode("FORM#DIVIDER" + UUID.randomUUID().toString());
            }
            widgetsMap.put(element.getElementCode(), widget);
        }
        // 初始化数据字典
        for (BaseFormWidget widget : widgetsMap.values()) {
            // initData配置
            Object initData = widget.getElement().getWidget().getObjectProperty(PROPERTY_INIT_DATA);
            if (initData != null) {
                try {
                    widget.initData(initData);
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 本地数据字典
            String dictArr = widget.getElement().getWidget().getStringProperty(PROPERTY_DICT_ARR);
            if (!StringUtil.isEmpty(dictArr)) {
                try {
                    Gson gson = new Gson();
                    List<DictItem> dictItems = gson.fromJson(dictArr, new TypeToken<ArrayList<DictItem>>(){}.getType());
                    if (dictItems != null) {
                        widget.initData(dictItems);
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 网络缓存数据字典
            String dictTypeCode = widget.getElement().getWidget().getStringProperty(PROPERTY_DICT_TYPE_CODE);
            if (!StringUtil.isEmpty(dictTypeCode)) {
                List items = getDictItemsOrTreeItems(dictTypeCode);
                if (items != null) {
                    widget.initData(items);
                }
            }
        }

        // 添加到容器
        for (Map.Entry<String, BaseFormWidget> entry : widgetsMap.entrySet()) {
            view_container.addView(entry.getValue().getView());
        }
        // 移除掉分隔符
        Iterator<Map.Entry<String, BaseFormWidget>> iterator = widgetsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getKey().contains("FORM#DIVIDER")) {
                iterator.remove();
            }
        }

        view.findViewById(R.id.btn_reset).setOnClickListener(v -> {
            onFilterReset(pageKey);
            for (BaseFormWidget widget : widgetsMap.values()) {
                widget.setValue("");
            }
        });
        view.findViewById(R.id.btn_finish).setOnClickListener(v -> {
            drawer_layout.closeDrawer(GravityCompat.END);
            onFilterFinish(pageKey);
        });
        ivClose = view.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.closeDrawer(GravityCompat.END);
            }
        });

    }

    protected abstract void onFilterReset(String pageKey);

    protected abstract void onFilterFinish(String pageKey);

    protected abstract void onFilterValueChange(String pageKey, BaseFormWidget widget, Object value, Object item, Boolean isEffective);

    public void showFilterView(String pageKey) {
        for (View view : mFilterViewMap.values()) {
            if (view != null) {
                view.setVisibility(View.GONE);
            }
        }
        View view = mFilterViewMap.get(pageKey);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }


    protected List<? extends IDictItem> getDictItemsOrTreeItems(String parentTypeCode) {
        List<? extends IDictItem> items = null;
        Dict dict = mDictRepository.getDictByTypeCode(parentTypeCode);
        if (dict != null) {
            if ("0".equals(dict.getTypeIsTree())) {
                items = mDictRepository.getDictItemByParentTypeCode(parentTypeCode);
            } else if ("1".equals(dict.getTypeIsTree())) {
                items = mDictRepository.getDictTreeItemByParentTypeCode(parentTypeCode);
            }
        }
        return items;
    }

    protected boolean showDrawer(boolean isShow) {
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            if (isShow) {
                return false;
            } else {
                drawer_layout.closeDrawer(GravityCompat.END);
                return true;
            }
        } else {
            if (isShow) {
                drawer_layout.openDrawer(GravityCompat.END);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!showDrawer(false) || drawer_layout==null) {
            super.onBackPressed();
        }
    }


    public Map<String, BaseFormWidget> getFilterWidgetMap(String pageTitle) {
        return mWidgetsMapMap.get(pageTitle);
    }

    public Map<String, String> getFilterWidgetValues(String pageTitle) {
        LinkedHashMap<String, BaseFormWidget> map = mWidgetsMapMap.get(pageTitle);
        if (map == null) return new HashMap<>();
        HashMap<String, String> filterParams = new HashMap<>();
        for (Map.Entry<String, BaseFormWidget> entry : map.entrySet()) {
            BaseFormWidget formWidget = entry.getValue();
            if (formWidget.getView().getVisibility() != View.VISIBLE) continue; // 目前不获取隐藏控件的值
            Object value = formWidget.getValue();
            if (formWidget.getView() instanceof AGTimeInterval
                    || formWidget.getView() instanceof AGTextInterval) {
                // 区间控件特殊处理
                String[] codes = formWidget.getElement().getElementCode().split(",");
                if (codes.length > 1) {
                    String[] values = ((String) value).split(",");
                    if (values.length == 0) {
                        filterParams.put(codes[0], "");
                        filterParams.put(codes[1], "");
                    } else if (values.length == 1) {
                        filterParams.put(codes[0], values[0]);
                        filterParams.put(codes[1], values[0]);
                    } else {
                        filterParams.put(codes[0], values[0]);
                        filterParams.put(codes[1], values[1]);
                    }
                } else {
                    filterParams.put(codes[0], value.toString());
                }
                continue;
            }
            if (value instanceof String) {
                filterParams.put(entry.getKey(), value.toString());
            } else if (value instanceof Map) {
                filterParams.putAll((Map<? extends String, ? extends String>) value);
            }
        }
        return filterParams;
    }

    /**
     * 获取指定页面的筛选控件
     *
     * @param pageTitle   页面标题
     * @param elementCode 元素编码
     * @return 筛选控件
     */
    public BaseFormWidget getFilterWidget(String pageTitle, String elementCode) {
        Map<String, BaseFormWidget> widgetMap = getFilterWidgetMap(pageTitle);
        for (BaseFormWidget baseFormWidget : widgetMap.values()) {
            if (baseFormWidget.getElement().getElementCode().equals(elementCode)) {
                return baseFormWidget;
            }
        }
        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public DrawerLayout getDrawerLayout(){
        return drawer_layout;
    }
}
