package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ProcessNewModel;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.AdviceListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @description 审批详情Fragment
 * @date: 20190402
 * @author: xieruibin
 */
public class AdviceListFragment extends BaseViewListFragment<EventProcess> {
    EventRepository mEventRepository;
    private EventListItem.DataBean mEventListItem;
    private EventInfoBean mEventBean;

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

    public void setEventListItem(EventListItem.DataBean eventListItem, EventInfoBean eventInfoBean) {
        mEventListItem = eventListItem;
        mEventBean = eventInfoBean;
    }

    @Override
    protected BaseViewListAdapter initAdapter() {
        return new AdviceListAdapter(getContext());
    }

    @Override
    protected void initView() {
        super.initView();
        mEventRepository = new EventRepository();
        RefreshLayout layout = this.refresh_layout;
        try{
            loadDatas(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    protected Observable<ApiResult<List<EventProcess>>> loadDatas(int page, int rows, Map filterParams) {
        refresh_layout.setNoMoreData(true);
        return mEventRepository.getAdviceList4_0(mEventBean == null ? "" : mEventBean.getApplyInfoVo().getApplyinstId(), mEventBean == null ? "" : mEventBean.getIteminstList().get(0).getProcinstId())
                .map(new Function<ApiResult<List<EventProcess>>, ApiResult<List<EventProcess>>>() {
                    @Override
                    public ApiResult<List<EventProcess>> apply(ApiResult<List<EventProcess>> listApiResult) throws Exception {
                        ApiResult<List<EventProcess>> newApiresult = new ApiResult<>();
                        newApiresult.setSuccess(listApiResult.isSuccess());
                        List<EventProcess> newList = new ArrayList<>();
                        List<EventProcess> data = listApiResult.getData();
                        if (!ListUtil.isEmpty(data)) {
                            for (EventProcess eventProcess : data) {
                                newList.addAll(getEventProcessList(eventProcess));
                            }
                        }
                        newApiresult.setData(newList);
                        return newApiresult;
                    }
                });

    }

    //遍历所有的审批节点
    private List<EventProcess> getEventProcessList(EventProcess data) {
        /**
         * data是一级流程，data.getChildNode()是二级流程，三级流程忽略
         */
        List<EventProcess> mList = new ArrayList<>();
        List<EventProcess> childNode = data.getChildNode();
        data.setNodeLevel(0);//一级流程
        EventProcess process = new EventProcess();

        if (childNode.size() > 1) {
            List<EventProcess> modelList = new ArrayList<>();
            int number = 1;//0 1 2 3 size = 4
            data.setProcessNewModel(new ProcessNewModel(childNode.get(0).getNodeName()));
            for (int i = 1; i < childNode.size(); i++) {
                if ((childNode.get(i).getNodeName()).equals(data.getProcessNewModel().getTitle())) {
                    number++;
                    modelList.add(childNode.get(i));
                }
            }
            if (number == childNode.size()) {
                data.getProcessNewModel().setEventProcesses(modelList);
                process.setProcessNewModel(data.getProcessNewModel());
                process.setTaskState(data.getTaskState());
                process.setCommentMessage(data.getCommentMessage());
                mList.add(process);
            } else {
                data.setProcessNewModel(null);
                mList.add(data); //添加一级流程
                for (int i = 0; i < childNode.size(); i++) {
                    EventProcess process1 = childNode.get(i); //二级
                    process1.setNodeLevel(1);//二级流程
                    if ("配建设施审查决定".equals(process1.getNodeName())) {
//                        Arrays.copyOf()
                        List<EventProcess> ConfigurationList = new ArrayList<>(); //存储三级
                        for (int i1 = 0; i1 < process1.getChildNode().size(); i1++) { //三级
                            ConfigurationList.add(process1.getChildNode().get(i1));
                        }
                        for (int i1 = 0; i1 < ConfigurationList.size(); i1++) {
                            ConfigurationList.get(i1).setNodeLevel(1);//二级流程
                            mList.add(ConfigurationList.get(i1));
                        }
                        process1.getChildNode().clear();
                    } else {
                        mList.add(process1);
                    }
//                    for (int j = 0; j < process1.getChildNode().size(); j++) {
//                        if("配建设施审查决定".equals(process1.getNodeName())) {
//                            List<EventProcess> ConfigurationList = process1.getChildNode();
//                            for (int i1 = 0; i1 < ConfigurationList.size(); i1++) {
//                                process1.getChildNode().get(i1).setNodeLevel(1);//二级流程
//                                mList.add(process1.getChildNode().get(i1));
//                                process1.getChildNode().remove(i1);
//                            }
//                        }
//                    }
//                    mList.addAll(childNode);
                }
            }
        } else {
            for (int i = 0; i < childNode.size(); i++) {
                childNode.get(i).setNodeLevel(1);//二级流程
            }
            mList.add(data);
            mList.addAll(childNode);
        }
        return mList;
    }


    @Override
    public void onItemClick(View itemView, int position, EventProcess data) {

    }

    @Override
    public boolean onItemLongClick(View itemView, int position, EventProcess data) {
        return false;
    }


    @Subscribe
    public void receive(EventInfoBean eventBean) {

        mEventBean = eventBean;
        if (refresh_layout != null) {
            loadDatas(true);
        }

    }
}
