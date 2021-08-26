package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ResultGoodsBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ZjzzBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ZjzzTypeBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.ZjzzListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static android.app.Activity.RESULT_OK;

/**
 * @description 证件证照Fragment
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class ZjzzListFragment extends BaseViewListFragment<ZjzzBean> {
    public static final int FLASH_CODE = 457;
    EventRepository mEventRepository;
    private EventInfoBean mData;
    private String iteminstId;
    private String applyinstId;
    private String taskId;
    private ZjzzTypeBean zjzzTypeBean;

    public static ZjzzListFragment getInstance(EventListItem.DataBean dataBean) {
        ZjzzListFragment mFragment = new ZjzzListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("applyinstId", dataBean.getApplyinstId());
        bundle.putString("taskId", dataBean.getTaskId());
        mFragment.setArguments(bundle);
        return mFragment;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    protected void initArguments() {
        super.initArguments();
        mEventRepository = new EventRepository();
        applyinstId = getArguments().getString("applyinstId");
        taskId = getArguments().getString("taskId");


    }

    @Override
    protected void initView() {
        super.initView();
        loadDatas(true);
        int state = getActivity().getIntent().getIntExtra("state", EventState.HANDLING);
        if (state== EventState.HANDLING) {
            btn_add_floating.setVisibility(View.VISIBLE);
            btn_add_floating.setOnClickListener(v -> {
                if (zjzzTypeBean != null) {
                    ZjzzTypeBean newZjzzBean = new ZjzzTypeBean();
                    newZjzzBean.setProjScale(zjzzTypeBean.getProjScale());
                    List<ZjzzTypeBean.CertListBean> cList = new ArrayList<>(zjzzTypeBean.getCertList());
                    for (ZjzzBean data : mDatas) {
                        for (ZjzzTypeBean.CertListBean certListBean : zjzzTypeBean.getCertList()) {
                            if (data.getMatId().equals(certListBean.getMatId())) {
                                cList.remove(certListBean);
                            }
                        }
                    }
                    newZjzzBean.setCertList(cList);
                    newZjzzBean.setOpuOmOrgList(zjzzTypeBean.getOpuOmOrgList());
                    EventApprovalActivity activity = (EventApprovalActivity) getActivity();
//                    List<ResultGoodsBean> resultGoodsBeans = activity.getResultGoodsBeans();
//                    newZjzzBean.setResultsList(resultGoodsBeans);
//                    if (cList.size()==0 && resultGoodsBeans.size()==0) {
//                        ToastUtil.longToast(getActivity(),"当前事项没有定义的证件类型或证件模板");
//                        return;
//                    }
                    Intent intent = new Intent(getActivity(), ZjzzAddActivity.class);
                    intent.putExtra("applyinstId", applyinstId);
                    intent.putExtra("iteminstId", iteminstId);
                    intent.putExtra("isSeriesApproval", mData.getApplyInfoVo().getIsSeriesApprove());//0并联1单项
                    intent.putExtra("taskId", taskId);
                    intent.putExtra(ZjzzAddActivity.ZJZZ_TYPE_BEAN,newZjzzBean);
                    getActivity().startActivityForResult(intent, FLASH_CODE);
                }else{
                    ToastUtil.longToast(getActivity(),"当前事项没有定义的证件类型或证件模板");
                    return;
                }

            });
        }

    }


    @Override
    protected BaseViewListAdapter initAdapter() {
        return new ZjzzListAdapter(getContext());
    }

    @Override
    protected Observable<ApiResult<List<ZjzzBean>>> loadDatas(int page, int rows, Map filterParams) {
        refresh_layout.setNoMoreData(true);

        //加载证照类型
        mEventRepository.getZjzzTypeList(applyinstId, iteminstId)
                .subscribe(zjzzTypeBeanApiResult -> {
                    if (zjzzTypeBeanApiResult.isSuccess()) {
                        zjzzTypeBean = zjzzTypeBeanApiResult.getData();
                    }
                }, throwable -> throwable.printStackTrace());
        return mEventRepository.getZjzzList(applyinstId, iteminstId)
                .map(listApiResult -> {
                    if (listApiResult.isSuccess() && listApiResult.getData()!=null) {
                        //排查结果物使用，存到activity
                        EventApprovalActivity activity = (EventApprovalActivity) getActivity();
//                        activity.reflashGoodsList(listApiResult.getData(),0);
                    }
                    return listApiResult;
                });

    }


    @Override
    public void onItemClick(View itemView, int position, ZjzzBean data) {

        Intent intent = new Intent(getActivity(), ZjzzAddActivity.class);
        intent.putExtra("applyinstId", applyinstId);
        intent.putExtra("iteminstId", iteminstId);
        intent.putExtra("data", data);
        if (zjzzTypeBean != null) {
            ZjzzTypeBean newZjzzBean = new ZjzzTypeBean();
            newZjzzBean.setProjScale(zjzzTypeBean.getProjScale());
            List<ZjzzTypeBean.CertListBean> cList = new ArrayList<>(zjzzTypeBean.getCertList());
            newZjzzBean.setCertList(cList);
            newZjzzBean.setOpuOmOrgList(zjzzTypeBean.getOpuOmOrgList());
            EventApprovalActivity activity = (EventApprovalActivity) getActivity();
//            List<ResultGoodsBean> resultGoodsBeans = activity.getResultGoodsBeanAll();
//            newZjzzBean.setResultsList(resultGoodsBeans);
//            intent.putExtra(ZjzzAddActivity.ZJZZ_TYPE_BEAN,newZjzzBean);
        }
        getActivity().startActivityForResult(intent, FLASH_CODE);


    }

    @Override
    public boolean onItemLongClick(View itemView, int position, ZjzzBean data) {

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("提示:")
                .setMessage("是否删除")
                .setNegativeButton("取消", (dialog1, which) -> {
                })
                .setPositiveButton("确定", (dialog12, which) -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("matinstIds", data.getMatinstId() == null ? "" : data.getMatinstId());
                    HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.DELETE_ZJZZ, map)
                            .subscribe(s -> {
                                if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                                    ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                                    }.getType());
                                    if (apiResult != null && apiResult.isSuccess()) {
                                        ToastUtil.shortToast(getActivity(), "删除成功");
                                        loadDatas(true);
                                    } else {
                                        ToastUtil.shortToast(getActivity(), "删除失败");
                                    }
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                ToastUtil.shortToast(getActivity(), "删除失败");
                            });
                })
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.agmobile_red));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.black));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);


        return false;
    }

//    @Subscribe
    public void receive(EventInfoBean mData) {
        this.mData = mData;
        applyinstId = mData.getApplyInfoVo().getApplyinstId();
        iteminstId = ListUtil.isEmpty(mData.getIteminstList()) ? "" : mData.getIteminstList().get(0).getIteminstId();
        if (refresh_layout != null) {
            loadDatas(true);
        }
    }

    public List<ZjzzBean> getData(){
        return mDatas;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            loadDatas(true);
        }
    }
}
