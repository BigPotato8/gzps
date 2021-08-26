package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.EventListAdapter;
import com.augurit.agmobile.busi.bpm.form.model.Element;
import com.augurit.agmobile.busi.bpm.form.model.FormInfo;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.bpm.widget.WidgetFactory;
import com.augurit.agmobile.busi.bpm.widget.WidgetType;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.common.lib.net.model.ApiResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;

import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITTEXT;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.TIMEPICKER;

/**
 * @description 事项列表Fragment
 * @date: 20190402
 * @author: xieruibin
 */
public class EventListFragment extends BaseViewListFragment<EventListBean> {
    List<EventBean> list;
    private EventRepository mEventRepository;
    private EventListItem.DataBean mEventListItem;
    private EventBean mEventBean;
    private List<BaseFormWidget> widgetList;


    public EventListFragment() {
        super();
        initDate();
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        mEventRepository = new EventRepository();
    }

    @Override
    protected void initView() {
        super.initView();
        //head_container.addView();//增加头部表单
        FormInfo formInfo = getFormInfo();
        initHeaderView(formInfo);

    }
    //加载头部表单
    private void initHeaderView(FormInfo formInfo) {
        WidgetFactory mWidgetFactory = new WidgetFactory();
        widgetList = new ArrayList<>();
        for (Element element : formInfo.getElements()) {
            BaseFormWidget widget = mWidgetFactory.create(getActivity(), element, true);
            if (widget == null) {
                continue;
            }
            widget.setFormState(FormState.STATE_READ);
            if (element.getWidget().getWidgetCode().equals(WidgetType.DIVIDER_GROUP)) {
                element.setElementCode("FORM#DIVIDER" + UUID.randomUUID().toString());
            }
            widgetList.add(widget);
            head_container.addView(widget.getView());
        }
    }

    //获取顶部的表单配置
    private FormInfo getFormInfo() {
        return new FormBuilder("")
                .addElement(new ElementBuilder("localCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("申报流水号")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("localCode")
                        .widget(new WidgetBuilder(TIMEPICKER)
                                .title("申报时间")
                                .formatDisplay("yyyy-MM-dd HH:mm")
                                .formatValue("")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("localCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("所属主题")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("localCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("申报阶段/辅线")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("localCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("申报事项数")
                                .isEnable(false)
                                .build())
                        .build())
                .build();
    }


    @Override
    protected BaseViewListAdapter<EventListBean> initAdapter() {
        return new EventListAdapter(getContext());
    }

    @Override
    protected Observable<ApiResult<List<EventListBean>>> loadDatas(int page, int rows, Map filterParams) {
        refresh_layout.setNoMoreData(true);
        return mEventRepository.getEvevtList(mEventBean == null ? "" : mEventBean.getApplyinstId(), "false", mEventListItem.getBusRecordId());

    }

    private void initDate() {
        list = new ArrayList<>();

    }

    @Override
    public void onItemClick(View itemView, int position, EventListBean data) {

    }

    @Override
    public boolean onItemLongClick(View itemView, int position, EventListBean data) {
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //head_container.addView(LayoutInflater.from(getActivity()).inflate(R.layout.item_gcjs_eventlist_header,null));
    }

    public void setEventListItem(EventListItem.DataBean eventListItem) {
        mEventListItem = eventListItem;
    }

    @Subscribe
    public void receive(EventBean eventBean) {
        mEventBean = eventBean;
        if (refresh_layout != null) {
            loadDatas(true);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}