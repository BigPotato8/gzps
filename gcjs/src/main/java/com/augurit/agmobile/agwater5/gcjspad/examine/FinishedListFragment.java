package com.augurit.agmobile.agwater5.gcjspad.examine;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsConstant;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjspad.MainPadActivity;
import com.augurit.agmobile.agwater5.gcjspad.event.FilterBeanEvent;
import com.augurit.agmobile.agwater5.gcjspad.event.ResetBeanEvent;
import com.augurit.agmobile.agwater5.gcjspad.examine.adapter.ExamineListAdapter;
import com.augurit.agmobile.agwater5.gcjspad.widget.PageControlView;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.lib.validate.StringUtil;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.agmobile.common.view.multispinner.AMMultiSpinner;
import com.augurit.common.common.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.examine
 * @createTime 创建时间 ：2020/12/8
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class FinishedListFragment extends BaseTaskFragment {
    protected ExamineListAdapter mFinishedAdapter;

    public static FinishedListFragment newInstance() {
        Bundle args = new Bundle();
        FinishedListFragment fragment = new FinishedListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initArguments() {
        mTitleName = "办结任务";
    }

    @Override
    protected void initData() {
        getFinishedList(1, mFilterMap);
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mTitleView.setText("办结任务");
        mFinishedAdapter = new ExamineListAdapter(R.layout.item_examine_right_list, 3);
        mRecyclerView.setAdapter(mFinishedAdapter);
        pc_page_control.setPageChangeListener(new PageControlView.OnPageChangeListener() {
            @Override
            public void pageChanged(PageControlView pageControl, int numPerPage) {
                if (curPage == numPerPage) {
                    ToastUtil.shortToast(getActivity(), "当前页面已加载");
                    return;
                }
                getFinishedList(numPerPage, mFilterMap);
            }
        });
        btn_jump_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = et_jump_index.getText().toString();
                if (TextUtils.isEmpty(number) || Integer.parseInt(number) > maxPage) {
                    ToastUtil.shortToast(getActivity(), "请输入合法的页码");
                    return;
                }
                getFinishedList(Integer.parseInt(number), mFilterMap);
            }
        });
        ll_spinner_sign_state.setVisibility(View.GONE);
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainPadActivity) getActivity()).showFilterView("办结任务");
                ((MainPadActivity) getActivity()).getDrawerLayout().openDrawer(GravityCompat.END);
            }
        });
        spinner_apply_type.setOnItemClickListener(new AMMultiSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, List<Object> selectedItems, Object item, boolean isSelected) {
                refreshFinishedList();
            }
        });
        spinner_InstState.setOnItemClickListener(new AMMultiSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, List<Object> selectedItems, Object item, boolean isSelected) {
                refreshFinishedList();
            }
        });
    }

    /**
     * 处理过滤参数并刷新列表
     */
    private void refreshFinishedList() {
        List<Object> currentApplyTypeSelected = spinner_apply_type.getCurrentSelected();//获取审批类型
        List<Object> currentInstStateSelected = spinner_InstState.getCurrentSelected();//获取审批时限
        Map<String, String> handedTaskFilterMap = ((MainPadActivity) getActivity()).getFilterWidgetValues("办结任务");
        handedTaskFilterMap.put("keyword", handedTaskFilterMap.get("keyword"));
        if (!ListUtil.isEmpty(currentApplyTypeSelected)) {
            String value = ((IDictItem) currentApplyTypeSelected.get(0)).getValue();
            handedTaskFilterMap.put("applyType", value);
        } else {
            handedTaskFilterMap.put("applyType", "");
        }
        if (!ListUtil.isEmpty(currentInstStateSelected)) {
            String value = ((IDictItem) currentInstStateSelected.get(0)).getValue();
            handedTaskFilterMap.put("instState", value);
        } else {
            handedTaskFilterMap.put("instState", "");
        }
        //受理时间处理
        if (handedTaskFilterMap.containsKey("acceptTime")) {
            String applyTime = handedTaskFilterMap.get("acceptTime");
            if (!TextUtils.isEmpty(applyTime)) {
                String[] split = applyTime.split(",");
                if (split.length >= 2) {
                    handedTaskFilterMap.put("acceptStartTime", split[0]);
                    handedTaskFilterMap.put("acceptEndTime", split[1]);
                }
            }
            if (handedTaskFilterMap.containsKey("acceptStartTime")) {
                String acceptStartTime = handedTaskFilterMap.get("acceptStartTime");
                if (StringUtil.isEmpty(acceptStartTime) || StringUtil.isTwoStringEqual("0", acceptStartTime)) {
                    handedTaskFilterMap.remove("acceptStartTime");
                } else {
                    handedTaskFilterMap.put("acceptStartTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(acceptStartTime))));
                }
            }
            if (handedTaskFilterMap.containsKey("acceptEndTime")) {
                String acceptEndTime = handedTaskFilterMap.get("acceptEndTime");
                if (StringUtil.isEmpty(acceptEndTime) || StringUtil.isTwoStringEqual("0", acceptEndTime)) {
                    handedTaskFilterMap.remove("acceptEndTime");
                } else {
                    handedTaskFilterMap.put("acceptEndTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(acceptEndTime))));
                }
            }
            handedTaskFilterMap.remove("acceptTime");
        }
        //实际办结时间处理
        if (handedTaskFilterMap.containsKey("concludeTime")) {
            String processTime = handedTaskFilterMap.get("concludeTime");
            if (!TextUtils.isEmpty(processTime)) {
                String[] split = processTime.split(",");
                if (split.length >= 2) {
                    handedTaskFilterMap.put("concludeStartTime", split[0]);
                    handedTaskFilterMap.put("concludeEndTime", split[1]);
                }
            }
            if (handedTaskFilterMap.containsKey("concludeStartTime")) {
                String concludeStartTime = handedTaskFilterMap.get("concludeStartTime");
                if (StringUtil.isEmpty(concludeStartTime) || StringUtil.isTwoStringEqual("0", concludeStartTime)) {
                    handedTaskFilterMap.remove("concludeStartTime");
                } else {
                    handedTaskFilterMap.put("concludeStartTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(concludeStartTime))));
                }
            }
            if (handedTaskFilterMap.containsKey("concludeEndTime")) {
                String concludeEndTime = handedTaskFilterMap.get("concludeEndTime");
                if (StringUtil.isEmpty(concludeEndTime) || StringUtil.isTwoStringEqual("0", concludeEndTime)) {
                    handedTaskFilterMap.remove("concludeEndTime");
                } else {
                    handedTaskFilterMap.put("concludeEndTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(concludeEndTime))));
                }
            }
            handedTaskFilterMap.remove("concludeTime");
        }
        Log.d("ywdev", handedTaskFilterMap.entrySet().toString());
        mFilterMap.clear();
        mFilterMap.putAll(handedTaskFilterMap);
        //加载列表并刷新
        getFinishedList(1, mFilterMap);
    }

    private void getFinishedList(int pageNum, HashMap<String, String> map) {
        Map<String, String> param = new HashMap<>();
        param.put("pageNum", pageNum + "");
        param.put("pageSize", pageSize + "");
        param.putAll(map);
        ProgressDialogUtil.showProgressDialog(getContext(),false);
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_BJ_LIST, param)
                .map(new Function<String, ApiResult<List<EventListItem.DataBean>>>() {
                    @Override
                    public ApiResult<List<EventListItem.DataBean>> apply(String s) throws Exception {
                        ApiResult<EventListItem> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<EventListItem>>() {
                        }.getType());

                        EventListItem data = apiResult.getData();
                        pc_page_control.setTotalPage(data.getPages());
                        tv_list_index_tip.setText("第" + data.getStartRow() + "-" + data.getEndRow() + "条/总共" + data.getPages() + "页");
                        ApiResult<List<EventListItem.DataBean>> listBeanApiResult = new ApiResult<>();
                        if (data != null) {
                            listBeanApiResult.setData(data.getList() == null ? new ArrayList() : data.getList());
                        } else {
                            listBeanApiResult.setData(new ArrayList<>());
                        }
                        curPage = pageNum;
                        maxPage = data.getPages();
                        pc_page_control.setSelectView(curPage);
                        if(!isDataLoadedFirst){
                            String total = String.valueOf(data.getTotal());
                            ((ExaminePadFragment)getParentFragment()).setChooseItemDatasCount("办结任务", total);
                            isDataLoadedFirst = true;
                        }
                        return listBeanApiResult;
                    }
                }).subscribe(new Consumer<ApiResult<List<EventListItem.DataBean>>>() {
                    @Override
                    public void accept(ApiResult<List<EventListItem.DataBean>> listApiResult) throws Exception {
                        List<EventListItem.DataBean> data = listApiResult.getData();
                        mFinishedAdapter.setNewData(data);
                        mRecyclerView.scrollToPosition(0);
                        if(data.size()==0){
                            mRecyclerView.setVisibility(View.GONE);
                            mNoDataViewGroup.setVisibility(View.VISIBLE);
                        }else {
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mNoDataViewGroup.setVisibility(View.GONE);
                        }
                        ProgressDialogUtil.dismissAll();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        mRecyclerView.setVisibility(View.GONE);
                        mNoDataViewGroup.setVisibility(View.VISIBLE);
                        ProgressDialogUtil.dismissAll();
                    }
                });
        compositeDisposable.add(subscribe);
    }





}
