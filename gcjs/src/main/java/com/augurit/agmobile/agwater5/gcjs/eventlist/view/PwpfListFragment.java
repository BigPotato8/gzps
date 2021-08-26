package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfTypeBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ResultGoodsBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.PwpfListAdapter;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.AGMultiSpinner;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;

/**
 * @description 批文批复Fragment
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class PwpfListFragment extends BaseViewListFragment<PwpfBean.ItemMatinstBean> {

    public static final int FLASH_CODE = 456;
    EventRepository mEventRepository;
    private EventInfoBean mData;
    private String iteminstId;
    private String applyinstId;
    private String taskId;
    private List<PwpfTypeBean> pwpfTypeBeanList;

    public static PwpfListFragment getInstance(EventListItem.DataBean dataBean) {
        PwpfListFragment mFragment = new PwpfListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("applyinstId", dataBean.getApplyinstId());
        bundle.putString("taskId", dataBean.getTaskId());
        mFragment.setArguments(bundle);
        return mFragment;
    }
//
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
        pwpfTypeBeanList = new ArrayList<>();
//        loadDatas(true);
        int state = getActivity().getIntent().getIntExtra("state", EventState.HANDLING);
        if (state== EventState.HANDLING) {
            btn_add_floating.setVisibility(View.VISIBLE);
            btn_add_floating.setOnClickListener(v -> {
                List<PwpfTypeBean> list = new ArrayList<>(pwpfTypeBeanList);
                for (PwpfBean.ItemMatinstBean itemMatinstBean : mDatas) {//排除掉已有类型
                    for (PwpfTypeBean pwpfTypeBean : pwpfTypeBeanList) {
                        if (itemMatinstBean.getMatId().equals(pwpfTypeBean.getMatId())) {
                            list.remove(pwpfTypeBean);
                        }
                    }
                }
                EventApprovalActivity activity = (EventApprovalActivity) getActivity();
//                List<ResultGoodsBean> resultGoodsBeans = activity.getResultGoodsBeans();
                Gson gson = new Gson();
//                String toJson = gson.toJson(resultGoodsBeans);
//                List<PwpfTypeBean> goodsList = gson.fromJson(toJson,new TypeToken<List<PwpfTypeBean>>(){}.getType());
//                list.addAll(goodsList);//增加通用结果物
                if (list.size()==0) {
                    ToastUtil.longToast(getContext(),"暂无可增加的批文类型");
                    return;
                }
                Intent intent = new Intent(getActivity(), PwpfAddActivity.class);
                intent.putExtra("applyinstId", applyinstId);
                intent.putExtra("iteminstId", iteminstId);
                intent.putExtra("isSeriesApproval", mData.getApplyInfoVo().getIsSeriesApprove());//0并联1单项
                intent.putExtra("taskId", taskId);

                intent.putExtra("typeList",(Serializable) list);

                getActivity().startActivityForResult(intent, FLASH_CODE);
            });
        }

    }

    @Override
    protected BaseViewListAdapter initAdapter() {
        return new PwpfListAdapter(getContext());
    }

    @Override
    protected Observable<ApiResult<List<PwpfBean.ItemMatinstBean>>> loadDatas(int page, int rows, Map filterParams) {
        refresh_layout.setNoMoreData(true);
//加载批文类型
        mEventRepository.getPwpfTypeList(applyinstId, iteminstId)
                .subscribe(new Consumer<ApiResult<List<PwpfTypeBean>>>() {
                    @Override
                    public void accept(ApiResult<List<PwpfTypeBean>> listApiResult) throws Exception {
                        if (listApiResult.isSuccess()) {
                            pwpfTypeBeanList = listApiResult.getData();
//                            AGMultiSpinner pwlxSpinner = (AGMultiSpinner) mFormPresenter.getWidget("matId").getView();
//                            DictBuilder dictBuilder = new DictBuilder();
//                            for (PwpfTypeBean bean : listApiResult.getData()) {
//                                dictBuilder.item(bean.getMatName(), bean.getMatId());
//                            }
//                            pwlxSpinner.initData(new ArrayList<IDictItem>(dictBuilder.build()));
//                            if (mData != null) {//若有数据，设置值
//                                pwlxSpinner.setValue(mData.getMatId());
//                            }
                        }
                    }
                }, throwable -> throwable.printStackTrace());
        return mEventRepository.getPwpfList(applyinstId,iteminstId)
                .map(listApiResult -> {
                    ApiResult<List<PwpfBean.ItemMatinstBean>> apiResult = new ApiResult<>();
                    apiResult.setCode(200);
                    ArrayList<PwpfBean.ItemMatinstBean> list = new ArrayList<>();
                    if (listApiResult.getData()!=null) {
                        for (PwpfBean pb : listApiResult.getData()) {
                            list.addAll(pb.getItemMatinst());
                        }
                    }
                    apiResult.setData(list);
                    //排查结果物使用，存到activity
                    EventApprovalActivity activity = (EventApprovalActivity) getActivity();
//                    activity.reflashGoodsList(list);

                    return apiResult;
                });

    }



    @Override
    public void onItemClick(View itemView, int position, PwpfBean.ItemMatinstBean data) {


        Intent intent = new Intent(getActivity(), PwpfAddActivity.class);
        intent.putExtra("applyinstId", applyinstId);
        intent.putExtra("iteminstId", iteminstId);
        intent.putExtra("data", data);

        getActivity().startActivityForResult(intent, FLASH_CODE);


    }

    @Override
    public boolean onItemLongClick(View itemView, int position, PwpfBean.ItemMatinstBean data) {

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("提示:")
                .setMessage("是否删除")
                .setNegativeButton("取消", (dialog1, which) -> {
                })
                .setPositiveButton("确定", (dialog12, which) -> {
                    Map<String, String> map = new HashMap<>();
//                    map.put("matinstId", data.getMatinstId() == null ? "" : data.getMatinstId());
                    HttpUtil.getInstance(AwUrlManager.serverUrl()).delete(GcjsUrlConstant.DELETE_PWPF+"/"+data.getMatinstId(), map)
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
                            },throwable -> {
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
        iteminstId = ListUtil.isEmpty(mData.getIteminstList())?"":mData.getIteminstList().get(0).getIteminstId();
        if (refresh_layout != null) {
            loadDatas(true);
        }
    }

    public List<PwpfBean.ItemMatinstBean> getData(){
        return mDatas;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            loadDatas(true);
        }
    }
}
