package com.augurit.agmobile.agwater5.gcjs.eventlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ProjIdBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.TaskIdBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.EventApprovalActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.common.common.util.ProgressDialogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import static android.app.Activity.RESULT_OK;

/**
 * com.augurit.agmobile.agwater5.gcjs.eventlist
 * Created by sdb on 2019/4/2  15:50.
 * Desc：待办事项列表fragment
 */

public class EventListFragment extends BaseViewListFragment<EventListItem.DataBean> {
    protected EventRepository mEventRepository;
    protected int mEventState;
    boolean isEffective=true;
    ImageView sortArrow;
    private boolean isAscend;
    public static EventListFragment newInstance(@EventState int eventState) {
        Bundle args = new Bundle();
        args.putInt(EventListActivity.EXTRA_EVENT_STATE, eventState);
        EventListFragment fragment = new EventListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mEventRepository = new EventRepository();
        View sortView = View.inflate(getActivity(), R.layout.fragment_sort, null);//排序view
        sortArrow = sortView.findViewById(R.id.sort_dowm);
        head_container.addView(sortView);
        ((View)sortArrow.getParent()).setOnClickListener(new View.OnClickListener() {//排序view的点击
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), isAscend? R.anim.arrow_to_down:R.anim.arrow_to_up);//变换箭头指向
                sortArrow.startAnimation(animation);//開始动画
                isEffective=false;
                loadDatas(true);//刷新列表
            }
        });
        return view;
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        if (getArguments() == null) return;
        mEventState = getArguments().getInt(EventListActivity.EXTRA_EVENT_STATE, EventState.HANDLING);
    }

    private Observable<ApiResult<List<EventListItem.DataBean>>> getEvnetList(String startRow, String pageSize, Map<String, String> filterParams) {

        Map<String, String> param = new HashMap<>();
//        if (!MapUtils.isEmpty(filterParams)) {
////            ToastUtil.shortToast(getActivity(),"筛选建设中");
//            //filterParams.remove("keyword");
//            int i = 0;
//            for (Map.Entry<String, String> entry : filterParams.entrySet()) {
//                if (entry.getKey() != null
//                        && !entry.getKey().isEmpty()
//                        && entry.getValue() != null
//                        && !entry.getValue().isEmpty()) {
//                    param.put("query[mykey][" + i + "][name]", "keyword1".equals(entry.getKey()) ? "keyword" : entry.getKey());
//                    param.put("query[mykey][" + i + "][value]", entry.getValue());
//                    i++;
//                }
//            }
//        }
        if (isEffective){
            //默认排序
            filterParams.put("keyword",filterParams.get("mkeyword"));//筛选条件的参数

            param.put("pageNum", startRow);
            param.put("pageSize", pageSize);
            param.putAll(filterParams);
        }else{//正序排序还是逆序排序取决于 sortStr
            filterParams.put("keyword",filterParams.get("mkeyword"));//筛选条件的参数
            filterParams.put("sortStr",isAscend? "1" : "2");

            param.put("pageNum", startRow);
            param.put("pageSize", pageSize);
            param.putAll(filterParams);
        }
        String url = "";
        switch (mEventState) {
            case EventState.HANDLING://待办
                url = GcjsUrlConstant.GET_EVENT_DB_LIST;
                break;
            case EventState.HANDLED://逾期
                param.put("instTimeState","3");
                url = GcjsUrlConstant.GET_EVENT_DB_LIST;
                break;
            case EventState.DEPARTMENT_ALL://预警
                param.put("instTimeState","2");
                url = GcjsUrlConstant.GET_EVENT_DB_LIST;
                break;
        }
        isAscend = !isAscend;
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(url, param)
                .map(s -> {
                    ApiResult<EventListItem> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<EventListItem>>() {
                    }.getType());

                    EventListItem data = apiResult.getData();
                    ApiResult<List<EventListItem.DataBean>> listBeanApiResult = new ApiResult<>();
                    if (data != null) {
                        listBeanApiResult.setData(data.getList() == null ? new ArrayList() : data.getList());
                    } else {
                        listBeanApiResult.setData(new ArrayList<>());
                    }
                    return listBeanApiResult;
                });

    }

    /**
     * 子类在此方法中初始化列表所需Adapter
     *
     * @return adapter
     */
    @Override
    protected BaseViewListAdapter<EventListItem.DataBean> initAdapter() {
        return new EventListAdapter(getContext(), mEventState);
    }

    /**
     * 子类在此方法中实现数据的加载<br>
     * 如果数据格式不符合ApiResult<List<T>>，可通过RxJava map操作符转换
     *
     * @param page         页数
     * @param rows         每页数量 {@link #mRows}
     * @param filterParams 筛选条件参数
     * @return 包含数据的Observable
     */
    @Override
    protected Observable<ApiResult<List<EventListItem.DataBean>>> loadDatas(int page, int rows, Map<String, String> filterParams) {
        return getEvnetList(page + "", rows + "", filterParams);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onItemClick(View itemView, int position, EventListItem.DataBean data) {
        String isHandleByMobile=data.getIsHandleByMobile();
        if (isHandleByMobile.equals("0")){
            ToastUtil.longToast(getActivity(),"移动端暂不支持此环节的办理");
        }else if (TextUtils.isEmpty(data.getApplyinstId())){
            ProgressDialogUtil.showProgressDialog(getActivity(),false);
            Map<String,String> param = new HashMap<>();
            param.put("iteminstId",data.getIteminstId());
            param.put("handler","false");
            HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_TASKID_BY_ITEMID, param)
                    .map(s -> {
                        ApiResult<TaskIdBean> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<TaskIdBean>>() {
                        }.getType());
                        return apiResult;
                    })
                    .subscribe(apiResult -> {
                        if (apiResult.isSuccess()) {
                            if (TextUtils.isEmpty(data.getTaskId())){
                                data.setTaskId(apiResult.getData().getTaskId());
                            }
                            Map<String,String> paramMap = new HashMap<>();
                            paramMap.put("isNotCompareAssignee","true");
                            HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_PROJECTID_BY_TASKID+"/"+apiResult.getData().getTaskId(), paramMap)
                                    .map(s ->{
                                        ApiResult<ProjIdBean> apiResult2 = new Gson().fromJson(s, new TypeToken<ApiResult<ProjIdBean>>() {
                                        }.getType());
                                        return apiResult2;
                                    })
                                    .subscribe(apiResult2 ->{
                                        ProgressDialogUtil.dismissAll();
                                        if (apiResult2.isSuccess()) {
                                            data.setApplyinstId(apiResult2.getData().getMasterEntityKey());
                                            data.setProcInstId(apiResult2.getData().getProcessInstanceId());
                                            Intent intent = new Intent(getActivity(), EventApprovalActivity.class);
                                            intent.putExtra("data", data);
                                            intent.putExtra("state", mEventState);
                                            getActivity().startActivityForResult(intent, mEventState);
                                        }else{
                                            ToastUtil.longToast(getActivity(),"获取项目信息失败");
                                        }
                                    },throwable -> {
                                        ProgressDialogUtil.dismissAll();
                                        throwable.printStackTrace();
                                        ToastUtil.longToast(getActivity(),"获取项目信息失败");
                                    });

                        }else{
                            ProgressDialogUtil.dismissAll();
                            ToastUtil.longToast(getActivity(),"获取项目信息失败");
                        }
                    },throwable -> {
                        ProgressDialogUtil.dismissAll();
                        throwable.printStackTrace();
                        ToastUtil.longToast(getActivity(),"获取项目信息失败");
                    })
            ;
        }else {
            Intent intent = new Intent(getActivity(), EventApprovalActivity.class);
            intent.putExtra("data", data);
            intent.putExtra("state", mEventState);
            getActivity().startActivityForResult(intent, mEventState);
        }
    }

    @Override
    public boolean onItemLongClick(View itemView, int position, EventListItem.DataBean data) {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (refresh_layout != null) {
                loadDatas(true);
            }
        }
    }
}
