package com.augurit.agmobile.agwater5.gcjspad.eventdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjspad.BasePadFragment;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter.EventDetailMaterialAdapter;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.model.ApproveStateBean;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.model.SmsInfo;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.loadinglayout.LoadingMaskLayout;
import com.augurit.common.common.util.ProgressDialogUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * 一套材料列表
 */
public class MaterialListPadFragment extends BasePadFragment {
    EventRepository eventRepository;
    String applyinstId;
    String iteminstId;
    String taskId;

    protected View mView;
    protected ViewGroup head_container;     // 头部控件容器
    protected RecyclerView rv_datas;
    protected RefreshLayout refresh_layout;
    protected LoadingMaskLayout mask_layout;
    protected ViewGroup loading_layout;
    protected ViewGroup btn_container;      // 右下角按钮容器
    protected ViewGroup btn_add_floating;   // 右下角添加按钮

    public static MaterialListPadFragment getInstance(String applyinstId,String taskId){
        MaterialListPadFragment fragment = new MaterialListPadFragment();
        fragment.eventRepository = new EventRepository();
        fragment.applyinstId = applyinstId;
        fragment.taskId = taskId;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.viewlist_fragment, container, false);
        initView();
        return mView;
    }

    private void initView() {
        rv_datas = mView.findViewById(R.id.rv_datas);
        head_container = mView.findViewById(R.id.head_container);
        rv_datas = mView.findViewById(R.id.rv_datas);
        refresh_layout = mView.findViewById(R.id.refresh_layout);
        mask_layout = mView.findViewById(R.id.mask_layout);
        loading_layout = mView.findViewById(R.id.loading_layout);
        btn_container = mView.findViewById(R.id.btn_container);
        btn_add_floating = mView.findViewById(R.id.btn_add_floating);

        refresh_layout.setEnableLoadMore(false);
        refresh_layout.setEnableRefresh(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        loadData();
    }

    private void loadData() {
        mask_layout.setVisibility(View.VISIBLE);
        mask_layout.showEmpty("",null);
        rv_datas.setVisibility(View.GONE);

    }

    public void loadData(String iteminstId){
        this.iteminstId = iteminstId;
        eventRepository.getMaterialFileList4_0(applyinstId,iteminstId)
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess()) {
                        if (ListUtil.isNotEmpty(listApiResult.getData())) {
                            mask_layout.setVisibility(View.GONE);
                            rv_datas.setVisibility(View.VISIBLE);

                            EventDetailMaterialAdapter adapter = new EventDetailMaterialAdapter(getActivity(),listApiResult.getData());
                            rv_datas.setAdapter(adapter);
                            rv_datas.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }else{
                            mask_layout.setVisibility(View.VISIBLE);
                            rv_datas.setVisibility(View.GONE);
                            mask_layout.showEmpty("暂无数据",null);
                        }
                    }else{
                        rv_datas.setVisibility(View.GONE);
                        mask_layout.setVisibility(View.VISIBLE);
                        mask_layout.showEmpty("暂无数据",null);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    mask_layout.setVisibility(View.VISIBLE);
                    rv_datas.setVisibility(View.GONE);
                    mask_layout.showError("加载失败",v -> loadData());
                });

    }


}
