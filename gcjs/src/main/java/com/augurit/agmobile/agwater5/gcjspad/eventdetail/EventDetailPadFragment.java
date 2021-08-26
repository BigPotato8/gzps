package com.augurit.agmobile.agwater5.gcjspad.eventdetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ElementButton;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ProjIdBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.EventApprovalActivity;
import com.augurit.agmobile.agwater5.gcjspad.BasePadFragment;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter.EventDetailMaterialAdapter;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.model.ApproveStateBean;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.model.SmsInfo;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.common.common.util.ProgressDialogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * 项目详情界面
 */
public class EventDetailPadFragment extends BasePadFragment {
    ImageView iv_back;
    TextView tv_source_value;
    TextView tv_status_value;
    TextView tv_project_name;

    Button btn_deal;
    Button btn_apply;
    Button btn_refuse;

    ViewPager vp_content_pad;
    TabLayout tab_layout;
    List<Fragment> fragments;
    private List<String> mTitles;
    EventListItem.DataBean listBean;
    private MaterialListPadFragment materialListPadFragment;
    private ApplicationFormDetailFragment applicationFormDetailFragment;
    EventRepository eventRepository;
    private String iteminstId;

    public static EventDetailPadFragment getInstance(EventListItem.DataBean listBean) {
        EventDetailPadFragment fragment = new EventDetailPadFragment();
        fragment.listBean = listBean;
        fragment.eventRepository = new EventRepository();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail_pad, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        iv_back = view.findViewById(R.id.iv_back);
        tv_source_value = view.findViewById(R.id.tv_source_value);
        tv_status_value = view.findViewById(R.id.tv_status_value);
        tv_project_name = view.findViewById(R.id.tv_project_name);
        btn_deal = view.findViewById(R.id.btn_deal);
        btn_apply = view.findViewById(R.id.btn_apply);
        btn_refuse = view.findViewById(R.id.btn_refuse);
        vp_content_pad = view.findViewById(R.id.vp_content_pad);
        tab_layout = view.findViewById(R.id.tab_layout);


    }

    private void initData() {
        if (listBean==null) {
            return;
        }
        iv_back.setOnClickListener(v -> {
            removeFragment(this);//关闭本界面
        });
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabTextView(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabTextView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tv_project_name.setText(listBean.getProjName());
        mTitles = new ArrayList<>();
        mTitles.add("申请表");
        mTitles.add("一套材料");
        mTitles.add("审批详情");
        mTitles.add("结果物");
        fragments = new ArrayList<>();
        materialListPadFragment = MaterialListPadFragment.getInstance(listBean.getApplyinstId(), listBean.getIteminstId());
        applicationFormDetailFragment = ApplicationFormDetailFragment.newInstance(listBean.getApplyinstId(), listBean.getTaskId());
        fragments.add(applicationFormDetailFragment);
        fragments.add(materialListPadFragment);
        fragments.add(ApproveStepPadFragment.newInstance(listBean.getApplyinstId(), listBean.getProcInstId()));
        fragments.add(ApproveResultFragment.newInstance(listBean.getApplyinstId(), listBean.getIteminstId()));
        MyPageAdapter adapter = new MyPageAdapter(getChildFragmentManager(), fragments);
        vp_content_pad.setAdapter(adapter);
        vp_content_pad.setOffscreenPageLimit(fragments.size());
        tab_layout.setupWithViewPager(vp_content_pad);
        setTabWidth(tab_layout, 30);
        //默认第一个,加粗
        updateTabTextView(tab_layout.getTabAt(0), true);
        if ("1.0".equals(listBean.getSignState())) {
            initFlow();
        }
        getIteninstID();
        getDetails(listBean.getApplyinstId(), listBean.getTaskId());
    }


    private void updateTabTextView(TabLayout.Tab tab, boolean isSelect) {
        //选中加粗
        try {
            Field mTabViewField = tab.getClass().getDeclaredField("mView");
            mTabViewField.setAccessible(true);
            Object tabView = mTabViewField.get(tab);

            Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
            mTextViewField.setAccessible(true);
            if (isSelect) {
                TextView tabSelect = (TextView) mTextViewField.get(tabView);
                tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tabSelect.setText(tab.getText());
            } else {
                TextView tabUnSelect = (TextView) mTextViewField.get(tabView);
                tabUnSelect.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tabUnSelect.setText(tab.getText());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    public void setTabWidth(final TabLayout tabLayout, final int padding) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = padding;
                        params.rightMargin = padding;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        private MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }


        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    //初始化工作流按钮
    @SuppressLint("CheckResult")
    private void initFlow() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("isNotCompareAssignee", "true");
        HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_PROJECTID_BY_TASKID + "/" + listBean.getTaskId(), paramMap)
                .map(s -> {
                    ApiResult<ProjIdBean> apiResult2 = new Gson().fromJson(s, new TypeToken<ApiResult<ProjIdBean>>() {
                    }.getType());
                    return apiResult2;
                })
                .subscribe(apiResult2 -> {
                    ProgressDialogUtil.dismissAll();
                    if (apiResult2.isSuccess() && apiResult2.getData() != null) {
                        Map<String, String> params = new HashMap<>();
                        params.put("privMode", "act-view");
                        params.put("version", apiResult2.getData().getCurrProcDefVersion());
                        params.put("viewId", listBean.getViewId());
                        params.put("actId", apiResult2.getData().getCurrTaskDefId());
                        params.put("appFlowdefId", apiResult2.getData().getAppFlowdefId());
                        eventRepository.getAuthorizeElementPlus(params)
                                .subscribe(listApiResult -> {
                                    if (listApiResult.isSuccess()) {//根据hidden==0加载按钮
                                        if (ListUtil.isNotEmpty(listApiResult.getData().getPanel())) {
                                            for (ElementButton.PanelBean panelBean : listApiResult.getData().getPanel()) {
                                                if ("0".equals(panelBean.getIsHidden()) && ListUtil.isNotEmpty(panelBean.getChildElement())) {
                                                    for (ElementButton.PanelBean bean : panelBean.getChildElement()) {
                                                        initFlowButton(bean);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }, throwable -> throwable.printStackTrace());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    private void initFlowButton(ElementButton.PanelBean bean) {
        if ("0".equals(bean.getIsHidden())) {
            switch (bean.getElementCode()) {
                case "win_wfBusSend"://办理(窗口)
                case "item_wfBusSend"://办理（部门）
                    btn_deal.setVisibility(View.VISIBLE);
                    btn_deal.setOnClickListener(v -> {
                        EventDealDialog dialog = new EventDealDialog(getActivity());
                        Map<String, String> params = new HashMap<>();
                        params.put("taskInstId", listBean.getTaskId());
                        params.put("title", "办理");
                        params.put("isDeal", "1");
                        dialog.setParams(params);
                        dialog.setSuccessListener(() -> removeFragment(EventDetailPadFragment.this));
                        dialog.show();
                    });
                    break;
//                case "win_shouli"://受理
//                case "item_shouli"://受理
                case "yushentongguo_chuangkou"://受理、预审通过（窗口）
                    btn_apply.setVisibility(View.VISIBLE);
                    btn_apply.setOnClickListener(v -> {
                        EventDealDialog dialog = new EventDealDialog(getActivity());
                        Map<String, String> params = new HashMap<>();
                        params.put("taskInstId", listBean.getTaskId());
                        params.put("title", "预审通过");
                        params.put("isDeal", "2");
                        params.put("processInstanceId", listBean.getProcInstId());
                        params.put("applyinstId", listBean.getApplyinstId());
                        params.put("iteminstId", iteminstId);
                        dialog.setParams(params);
                        dialog.setSuccessListener(() -> removeFragment(EventDetailPadFragment.this));
                        dialog.show();
                    });
                    break;
//                case "win_buyushouli"://不予受理
//                case "item_buyushouli"://不予受理
                case "buyushouli_chuangkou"://不予受理(窗口)
                    btn_refuse.setVisibility(View.VISIBLE);
                    btn_refuse.setOnClickListener(v -> {
                        EventDealDialog dialog = new EventDealDialog(getActivity());
                        Map<String, String> params = new HashMap<>();
                        params.put("taskInstId", listBean.getTaskId());
                        params.put("title", "不予受理");
                        params.put("isDeal", "3");
                        params.put("processInstanceId", listBean.getProcInstId());
                        params.put("applyinstId", listBean.getApplyinstId());
                        params.put("iteminstId", iteminstId);
                        dialog.setParams(params);
                        dialog.setSuccessListener(() -> removeFragment(EventDetailPadFragment.this));
                        dialog.show();
                    });
                    break;
                default:
                    break;
            }
        }
    }

    //获取事项id
    private void getIteninstID() {
        eventRepository.getEventItemId4_0(listBean.getTaskId())
                .subscribe(apiResult -> {
                    if (apiResult.isSuccess()) {
                        iteminstId = apiResult.getData();
                    }
                    materialListPadFragment.loadData(iteminstId);
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    //加载申请表详情
    private void getDetails(String applyinstId, String taskId) {
        ProgressDialogUtil.showProgressDialog(getActivity(), "正在加载详情", false);
        Observable<ApiResult<SmsInfo>> smsInfo = eventRepository.getSmsInfo(applyinstId);
        Observable<ApiResult<EventInfoBean>> baseInfo = eventRepository.getBaseInfo(applyinstId, taskId);
        Observable<ApiResult<ApproveStateBean>> approveStateBean = eventRepository.getApproveStateBean(applyinstId, taskId);
        Disposable subscribe = Observable.zip(smsInfo, baseInfo, approveStateBean, new Function3<ApiResult<SmsInfo>, ApiResult<EventInfoBean>, ApiResult<ApproveStateBean>, Object[]>() {

            @Override
            public Object[] apply(ApiResult<SmsInfo> smsInfoApiResult, ApiResult<EventInfoBean> eventInfoBeanApiResult, ApiResult<ApproveStateBean> approveStateBeanApiResult) throws Exception {
                Object[] result = new Object[3];
                result[0] = smsInfoApiResult;
                result[1] = eventInfoBeanApiResult;
                result[2] = approveStateBeanApiResult;
                return result;
            }
        }).subscribe(objects -> {
            ApiResult<SmsInfo> smsInfoApiResult = (ApiResult<SmsInfo>) objects[0];
            ApiResult<EventInfoBean> eventInfoBeanApiResult = (ApiResult<EventInfoBean>) objects[1];
            ApiResult<ApproveStateBean> stateBeanApiResult = (ApiResult<ApproveStateBean>) objects[2];
            //设置来源和状态
            if (stateBeanApiResult.isSuccess() && stateBeanApiResult.getData() != null) {
                String applyStyle = stateBeanApiResult.getData().getApplyStyle();
                tv_source_value.setText("win".equals(applyStyle) ? "窗口申报" : "网上申报");
                tv_status_value.setText(stateBeanApiResult.getData().getCurrentState());
            }
            if (smsInfoApiResult.isSuccess() && eventInfoBeanApiResult.isSuccess()) {
                applicationFormDetailFragment.showDetail(smsInfoApiResult.getData(), eventInfoBeanApiResult.getData(), stateBeanApiResult.getData());
            } else {
                ToastUtil.shortToast(getContext(), "加载详情失败");
            }

            ProgressDialogUtil.dismissAll();
        }, throwable -> {
            throwable.printStackTrace();
            ToastUtil.shortToast(getContext(), "加载详情失败");
            ProgressDialogUtil.dismissAll();
        });
        compositeDisposable.add(subscribe);
    }
}
