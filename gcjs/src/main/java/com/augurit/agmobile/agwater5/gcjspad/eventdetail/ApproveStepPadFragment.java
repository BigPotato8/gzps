package com.augurit.agmobile.agwater5.gcjspad.eventdetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.view.NavigationView;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjspad.BasePadFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.validate.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门审批fragemnt
 */
public class ApproveStepPadFragment extends BasePadFragment {

    private NavigationView nav_view;
    private FrameLayout fl_content;
    private List<ApprovePerStepPadFragment> fragments;
    private EventRepository eventRepository;
    private String mApplyinstId;
    private String mProinstId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mApplyinstId = getArguments().getString("applyinstId");
            mProinstId = getArguments().getString("procinstId");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static ApproveStepPadFragment newInstance(String applyinstId, String procinstId) {
        Bundle args = new Bundle();
        ApproveStepPadFragment fragment = new ApproveStepPadFragment();
        args.putString("applyinstId", applyinstId);
        args.putString("procinstId", procinstId);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approve_step_pad, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @SuppressLint("CheckResult")
    private void initView(View view) {
        nav_view = view.findViewById(R.id.nav_view);
        fl_content = view.findViewById(R.id.fl_content);
        fragments = new ArrayList<>();
        eventRepository = new EventRepository();

        if (eventRepository != null) {
            eventRepository.getAdviceList4_0(TextUtils.isEmpty(mApplyinstId) ? "" : mApplyinstId, TextUtils.isEmpty(mProinstId) ? "" : mProinstId)
                    .subscribe(listApiResult -> {
                        if (listApiResult.isSuccess()) {//获取审批列表成功
                            initDatas(listApiResult);
                        } else {

                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                    });
        }

    }

    private void initDatas(ApiResult<List<EventProcess>> listApiResult) {
        if (ListUtil.isNotEmpty(listApiResult.getData())) {
            List<String> nameList = new ArrayList<>();
            List<EventProcess> data = listApiResult.getData();
            for (EventProcess eventProcess : data) {
                nameList.add(eventProcess.getNodeName());
                fragments.add(ApprovePerStepPadFragment.getInstance(eventProcess));
            }
            boolean onlyOne = false;
            if (nameList.size() == 1) {//只有窗口节点，增加一个部门节点
                onlyOne = true;
                nameList.add("部门审批");
            }
            String[] strings = new String[nameList.size()];
            strings = nameList.toArray(strings);
            nav_view.setNaviTextArrs(strings);
            if (!onlyOne) {
                nav_view.setStepIndex(strings.length - 1);//最后一步总在进行中
                nav_view.setCurrentState(strings.length - 1);
            } else {
                nav_view.setStepIndex(strings.length - 2);
                nav_view.setCurrentState(strings.length - 2);
            }
            nav_view.setOnItemClickListener((position, view2) -> {
                //切换fragment
                getChildFragmentManager().beginTransaction().
                        replace(R.id.fl_content, fragments.get(position)).commit();
            });
            //默认打开最后一个节点
            getChildFragmentManager().beginTransaction().
                    replace(R.id.fl_content, fragments.get(fragments.size() - 1)).commit();
        }

    }
}
