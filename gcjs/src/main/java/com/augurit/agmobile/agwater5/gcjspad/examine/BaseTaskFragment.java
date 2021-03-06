package com.augurit.agmobile.agwater5.gcjspad.examine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjspad.BasePadFragment;
import com.augurit.agmobile.agwater5.gcjspad.MainPadActivity;
import com.augurit.agmobile.agwater5.gcjspad.event.FilterBeanEvent;
import com.augurit.agmobile.agwater5.gcjspad.event.ResetBeanEvent;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.EventDetailPadFragment;
import com.augurit.agmobile.agwater5.gcjspad.examine.listener.OnTitleClickListener;
import com.augurit.agmobile.agwater5.gcjspad.widget.PageControlView;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.lib.validate.StringUtil;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.agmobile.common.view.multispinner.AMMultiSpinner;
import com.augurit.agmobile.common.view.widget.WEUIButton;
import com.augurit.common.common.util.TimeUtil;
import com.augurit.common.dict.DictUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author ????????? ???yaowang
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.agwater5.gcjspad.examine
 * @createTime ???????????? ???2020/12/8
 * @modifyBy ????????? ???
 * @modifyTime ???????????? ???
 * @modifyMemo ???????????????
 */
public abstract class BaseTaskFragment extends BasePadFragment {
    protected int curPage = 0;
    protected int maxPage = 0;
    protected int pageSize = 10;
    protected TextView mTitleView;
    protected OnTitleClickListener onTitleClickListener;

    protected TextView tv_list_index_tip;//????????????
    protected PageControlView pc_page_control;//????????????
    protected EditText et_jump_index;//???????????????????????????
    protected WEUIButton btn_jump_confirm;//????????????
    protected ViewGroup rl_page_control;//??????????????????

    protected RecyclerView mRecyclerView;
    protected ViewGroup mNoDataViewGroup;//????????????

    /**
     * ????????????
     */
    protected ViewGroup ll_spinner_sign_state;
    protected AMMultiSpinner spinner_sign_state;
    /**
     * ????????????
     */
    protected ViewGroup ll_spinner_apply_type;
    protected AMMultiSpinner spinner_apply_type;
    /**
     * ????????????
     */
    protected ViewGroup ll_spinner_InstState;
    protected AMMultiSpinner spinner_InstState;

    protected ImageView iv_filter;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected HashMap<String, String> mFilterMap = new HashMap<>();
    protected boolean isViewInit = false;
    protected boolean isVisibleToUser = false;
    protected boolean isDataLoadedFirst = false;
    /**
     * ?????????
     */
    protected String mTitleName = "";
    /**
     * ?????????????????????
     */
    protected BaseQuickAdapter mBaseAdapter;
    /**
     * ?????????????????????
     */
    protected String mBaseUrl = "";
    /**
     * ??????????????????
     */
    protected HashMap<String, String> mBaseMap = new HashMap<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //xzw ????????????????????????????????????
        initArguments();
        initView(view);
        initListener();
        if (!isViewInit && isVisibleToUser) {//???????????????????????????view?????????????????????????????????????????????
            initData();
            isViewInit = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && !isViewInit) {//???????????????????????????view????????????????????????view???????????????????????????
            if (isResumed()) {
                initData();
                isViewInit = true;
            }
        }
    }


    protected abstract void initArguments();

    protected abstract void initData();

    protected void initView(View view) {
        mTitleView = view.findViewById(R.id.tv_examine_fun_title);
        mRecyclerView = view.findViewById(R.id.rv_list_content);
        mNoDataViewGroup = view.findViewById(R.id.ll_no_data);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTitleClickListener != null) {
                    onTitleClickListener.onTitleClick();
                }
            }
        });
        mTitleView.setText(mTitleName);
        //??????????????????
        rl_page_control = view.findViewById(R.id.rl_page_control);
        tv_list_index_tip = view.findViewById(R.id.tv_list_index_tip);
        pc_page_control = view.findViewById(R.id.pc_page_control);
        et_jump_index = view.findViewById(R.id.et_jump_index);
        btn_jump_confirm = view.findViewById(R.id.btn_jump_confirm);

        //??????????????????
        ll_spinner_sign_state = view.findViewById(R.id.ll_spinner_sign_state);
        ll_spinner_apply_type = view.findViewById(R.id.ll_spinner_apply_type);
        ll_spinner_InstState = view.findViewById(R.id.ll_spinner_InstState);
        spinner_sign_state = view.findViewById(R.id.spinner_sign_state);
        spinner_apply_type = view.findViewById(R.id.spinner_apply_type);
        spinner_InstState = view.findViewById(R.id.spinner_InstState);
        spinner_sign_state.setHint("????????????");
        spinner_sign_state.setAllowCancelCheck(true);
        spinner_sign_state.setAllowSelectCount(1);
        initData(DictUtil.getSignState(), spinner_sign_state);
        spinner_apply_type.setHint("????????????");
        spinner_apply_type.setAllowCancelCheck(true);
        spinner_apply_type.setAllowSelectCount(1);
        initData(DictUtil.getApplyType(), spinner_apply_type);
        spinner_InstState.setHint("????????????");
        spinner_InstState.setAllowCancelCheck(true);
        spinner_InstState.setAllowSelectCount(1);
        initData(DictUtil.getInstState(), spinner_InstState);

        //??????????????????
        iv_filter = view.findViewById(R.id.iv_filter);


    }

    protected void initListener() {
        //???????????????
        pc_page_control.setPageChangeListener((pageControl, numPerPage) -> {
            if (curPage == numPerPage) {
                ToastUtil.shortToast(getActivity(), "?????????????????????");
                return;
            }
            getDataList(numPerPage, mFilterMap);
        });

        //????????????
        btn_jump_confirm.setOnClickListener(v -> {
            String number = et_jump_index.getText().toString();
            if (TextUtils.isEmpty(number) || Integer.parseInt(number) > maxPage) {
                ToastUtil.shortToast(getActivity(), "????????????????????????");
                return;
            }
            getDataList(Integer.parseInt(number), mFilterMap);
        });

        //??????????????????
        iv_filter.setOnClickListener(v -> {
            ((MainPadActivity) getActivity()).showFilterView(mTitleName);
            ((MainPadActivity) getActivity()).getDrawerLayout().openDrawer(GravityCompat.END);
        });

        //????????????
        spinner_apply_type.setOnItemClickListener((position, selectedItems, item, isSelected) -> {
            refreshList(1);
        });

        //????????????
        spinner_InstState.setOnItemClickListener((position, selectedItems, item, isSelected) -> {
            refreshList(1);
        });
        //????????????
        spinner_sign_state.setOnItemClickListener(new AMMultiSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position, List<Object> selectedItems, Object item, boolean isSelected) {
                refreshList(1);
            }
        });
        //????????????
        mBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //?????????????????????
                Object item = adapter.getData().get(position);
                jumpToDetailFragment((EventListItem.DataBean) item);

            }
        });

    }

    /**
     * ??????????????????
     *
     * @param item
     */
    protected void jumpToDetailFragment(EventListItem.DataBean item) {
        EventDetailPadFragment eventDetailPadFragment = EventDetailPadFragment.getInstance(item);
        addFragmentOnActivity(eventDetailPadFragment);
    }

    protected void setOnTitleClickListener(OnTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
    }

    public void initData(List<DictItem> data, AMMultiSpinner amMultiSpinner) {
        LinkedHashMap<String, Object> itemsMap = new LinkedHashMap<>();
        if (data != null) {
            for (DictItem dictItem : data) {
                itemsMap.put(dictItem.getLabel(), dictItem);
            }
        }
        amMultiSpinner.setItemsMap(itemsMap);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        compositeDisposable.clear();
//        mBaseMap.clear();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * ??????????????????
     *
     * @param pageNum ??????
     * @param map     ????????????
     */
    public void getDataList(int pageNum, HashMap<String, String> map) {
        Map<String, String> param = new HashMap<>();
        param.put("pageNum", pageNum + "");
        param.put("page", pageNum + "");
        param.put("pageSize", pageSize + "");
        param.putAll(mBaseMap);
        param.putAll(map);
        ProgressDialogUtil.showProgressDialog(getContext(), false);
        if (TextUtils.isEmpty(mBaseUrl)) {
            return;
        }
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(mBaseUrl, param)
                .map(new Function<String, ApiResult<List<EventListItem.DataBean>>>() {
                    @Override
                    public ApiResult<List<EventListItem.DataBean>> apply(String s) throws Exception {
                        ApiResult<EventListItem> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<EventListItem>>() {
                        }.getType());

                        EventListItem data = apiResult.getData();
                        pc_page_control.setTotalPage(data.getPages());
                        tv_list_index_tip.setText("???" + data.getStartRow() + "-" + data.getEndRow() + "???/??????" + data.getPages() + "???");
                        ApiResult<List<EventListItem.DataBean>> listBeanApiResult = new ApiResult<>();
                        if (data != null) {
                            listBeanApiResult.setData(data.getList() == null ? new ArrayList() : data.getList());
                        } else {
                            listBeanApiResult.setData(new ArrayList<>());
                        }
                        curPage = pageNum;
                        maxPage = data.getPages();
                        pc_page_control.setSelectView(curPage);
                        if (!isDataLoadedFirst) {
                            String total = String.valueOf(data.getTotal());
                            ((ExaminePadFragment) getParentFragment()).setChooseItemDatasCount(mTitleName, total);
                            isDataLoadedFirst = true;
                        }
                        return listBeanApiResult;
                    }
                }).subscribe(new Consumer<ApiResult<List<EventListItem.DataBean>>>() {
                    @Override
                    public void accept(ApiResult<List<EventListItem.DataBean>> listApiResult) throws Exception {
                        List<EventListItem.DataBean> data = listApiResult.getData();
                        mBaseAdapter.setNewData(data);
                        mRecyclerView.scrollToPosition(0);
                        if (data.size() == 0) {
                            mRecyclerView.setVisibility(View.GONE);
                            mNoDataViewGroup.setVisibility(View.VISIBLE);
                        } else {
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


    /**
     * ????????????
     */
    protected void refreshList(int page) {
        List<Object> currentSignStateSelected = spinner_sign_state.getCurrentSelected();//??????????????????
        List<Object> currentApplyTypeSelected = spinner_apply_type.getCurrentSelected();//??????????????????
        List<Object> currentInstStateSelected = spinner_InstState.getCurrentSelected();//??????????????????
        Map<String, String> taskFilterMap = ((MainPadActivity) getActivity()).getFilterWidgetValues(mTitleName);
        if (spinner_sign_state.getVisibility() == View.VISIBLE) {
            if (!ListUtil.isEmpty(currentSignStateSelected)) {
                String value = ((IDictItem) currentSignStateSelected.get(0)).getValue();
                taskFilterMap.put("signState", value);
            } else {
                taskFilterMap.put("signState", "");
            }
        }
        if (spinner_apply_type.getVisibility() == View.VISIBLE) {
            if (!ListUtil.isEmpty(currentApplyTypeSelected)) {
                String value = ((IDictItem) currentApplyTypeSelected.get(0)).getValue();
                taskFilterMap.put("applyType", value);
            } else {
                taskFilterMap.put("applyType", "");
            }
        }
        if (spinner_InstState.getVisibility() == View.VISIBLE) {
            if (!ListUtil.isEmpty(currentInstStateSelected)) {
                String value = ((IDictItem) currentInstStateSelected.get(0)).getValue();
                taskFilterMap.put("instState", value);
            } else {
                taskFilterMap.put("instState", "");
            }
        }
        //??????????????????
        if (taskFilterMap.containsKey("applyTime")) {
            String applyTime = taskFilterMap.get("applyTime");
            if (!TextUtils.isEmpty(applyTime)) {
                String[] split = applyTime.split(",");
                if (split.length >= 2) {
                    taskFilterMap.put("applyStartTime", split[0]);
                    taskFilterMap.put("applyEndTime", split[1]);
                }
            }
            if (taskFilterMap.containsKey("applyStartTime")) {
                String applyStartTime = taskFilterMap.get("applyStartTime");
                if (StringUtil.isEmpty(applyStartTime) || StringUtil.isTwoStringEqual("0", applyStartTime)) {
                    taskFilterMap.remove("applyStartTime");
                } else {
                    taskFilterMap.put("applyStartTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(applyStartTime))));
                }
            }
            if (taskFilterMap.containsKey("applyEndTime")) {
                String applyEndTime = taskFilterMap.get("applyEndTime");
                if (StringUtil.isEmpty(applyEndTime) || StringUtil.isTwoStringEqual("0", applyEndTime)) {
                    taskFilterMap.remove("applyEndTime");
                } else {
                    taskFilterMap.put("applyEndTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(applyEndTime))));
                }
            }

            taskFilterMap.remove("applyTime");
        }
        //??????????????????
        if (taskFilterMap.containsKey("acceptTime")) {
            String applyTime = taskFilterMap.get("acceptTime");
            if (!TextUtils.isEmpty(applyTime)) {
                String[] split = applyTime.split(",");
                if (split.length >= 2) {
                    taskFilterMap.put("acceptStartTime", split[0]);
                    taskFilterMap.put("acceptEndTime", split[1]);
                }
            }
            if (taskFilterMap.containsKey("acceptStartTime")) {
                String applyStartTime = taskFilterMap.get("acceptStartTime");
                if (StringUtil.isEmpty(applyStartTime) || StringUtil.isTwoStringEqual("0", applyStartTime)) {
                    taskFilterMap.remove("acceptStartTime");
                } else {
                    taskFilterMap.put("acceptStartTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(applyStartTime))));
                }
            }
            if (taskFilterMap.containsKey("acceptEndTime")) {
                String applyEndTime = taskFilterMap.get("acceptEndTime");
                if (StringUtil.isEmpty(applyEndTime) || StringUtil.isTwoStringEqual("0", applyEndTime)) {
                    taskFilterMap.remove("acceptEndTime");
                } else {
                    taskFilterMap.put("acceptEndTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(applyEndTime))));
                }
            }

            taskFilterMap.remove("applyTime");
        }
        //????????????????????????
        if (taskFilterMap.containsKey("dismissedTime")) {
            String applyTime = taskFilterMap.get("dismissedTime");
            if (!TextUtils.isEmpty(applyTime)) {
                String[] split = applyTime.split(",");
                if (split.length >= 2) {
                    taskFilterMap.put("dismissedStartTime", split[0]);
                    taskFilterMap.put("dismissedEndTime", split[1]);
                }
            }
            if (taskFilterMap.containsKey("dismissedStartTime")) {
                String applyStartTime = taskFilterMap.get("dismissedStartTime");
                if (StringUtil.isEmpty(applyStartTime) || StringUtil.isTwoStringEqual("0", applyStartTime)) {
                    taskFilterMap.remove("dismissedStartTime");
                } else {
                    taskFilterMap.put("dismissedStartTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(applyStartTime))));
                }
            }
            if (taskFilterMap.containsKey("dismissedEndTime")) {
                String applyEndTime = taskFilterMap.get("dismissedEndTime");
                if (StringUtil.isEmpty(applyEndTime) || StringUtil.isTwoStringEqual("0", applyEndTime)) {
                    taskFilterMap.remove("dismissedEndTime");
                } else {
                    taskFilterMap.put("dismissedEndTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(applyEndTime))));
                }
            }

            taskFilterMap.remove("dismissedTime");
        }
        //????????????????????????
        if (taskFilterMap.containsKey("processTime")) {
            String processTime = taskFilterMap.get("processTime");
            if (!TextUtils.isEmpty(processTime)) {
                String[] split = processTime.split(",");
                if (split.length >= 2) {
                    taskFilterMap.put("processStartTime", split[0]);
                    taskFilterMap.put("processEndTime", split[1]);
                }
            }
            if (taskFilterMap.containsKey("processStartTime")) {
                String processStartTime = taskFilterMap.get("processStartTime");
                if (StringUtil.isEmpty(processStartTime) || StringUtil.isTwoStringEqual("0", processStartTime)) {
                    taskFilterMap.remove("processStartTime");
                } else {
                    taskFilterMap.put("processStartTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(processStartTime))));
                }
            }
            if (taskFilterMap.containsKey("processEndTime")) {
                String processEndTime = taskFilterMap.get("processEndTime");
                if (StringUtil.isEmpty(processEndTime) || StringUtil.isTwoStringEqual("0", processEndTime)) {
                    taskFilterMap.remove("processEndTime");
                } else {
                    taskFilterMap.put("processEndTime", TimeUtil.getStringTimeYMD(new Date(Long.parseLong(processEndTime))));
                }
            }
            taskFilterMap.remove("processTime");
        }
        Log.d("ywdev", taskFilterMap.entrySet().toString());
        mFilterMap.clear();

        mFilterMap.putAll(taskFilterMap);
        mFilterMap.putAll(mBaseMap);
        //?????????????????????
        getDataList(page, mFilterMap);
    }

    /**
     * ??????????????????
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveFilterEvent(FilterBeanEvent event) {
        if (mTitleName.equals(event.getPageKey())) {
            refreshList(1);
        }
    }

    /**
     * ????????????
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveResetEvent(ResetBeanEvent event) {
        if (mTitleName.equals(event.getPageKey())) {

        }
    }

    /**
     * ????????????
     *
     * @param taskid
     */
    protected void singTask(String taskid) {
        ProgressDialogUtil.showProgressDialog(getContext(), false);
        Disposable disposable = HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.SIGN_TASK + "/" + taskid, null)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ApiResult<String> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<String>>() {
                        }.getType());
                        if (apiResult.isSuccess()) {
                            ToastUtil.shortToast(getContext(), "????????????");
                        } else {
                            ToastUtil.shortToast(getContext(), "??????????????????????????????" + apiResult.getMessage());
                        }
                        ProgressDialogUtil.dismissAll();
                        refreshList(curPage);
                    }
                });
        compositeDisposable.add(disposable);
    }
}
