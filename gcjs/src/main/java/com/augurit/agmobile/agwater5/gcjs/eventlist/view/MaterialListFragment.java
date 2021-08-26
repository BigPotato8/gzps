package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.augurit.agmobile.agwater5.gcjs.eventlist.model.Attachment;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialListBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.MaterialListAdapter;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.bpm.workflow.model.WorkflowState;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @description 材料列表Fragment
 * @date: 20190402
 * @author: xieruibin
 */
public class MaterialListFragment extends BaseViewListFragment<MaterialListBean> {
    public final static int FLASH_RESULT_CODE = 1233;
    private EventRepository mEventRepository;
    private String mIsSeriesApproval;
    private EventInfoBean mEventBean;

    public static MaterialListFragment getInstance(String isSeriesApproval) {
        MaterialListFragment materialListFragment = new MaterialListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("isSeriesApproval", isSeriesApproval);
        materialListFragment.setArguments(bundle);
        return materialListFragment;
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

    @Override
    protected void initArguments() {
        super.initArguments();
        mEventRepository = new EventRepository();

        mIsSeriesApproval = getArguments().getString("isSeriesApproval");//isSeriesApprove=0并联，isSeriesApprove=1单项
    }

    @Override
    protected BaseViewListAdapter<MaterialListBean> initAdapter() {
        return new MaterialListAdapter(getContext());
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected Observable<ApiResult<List<MaterialListBean>>> loadDatas(int page, int rows, Map filterParams) {
        refresh_layout.setEnableLoadMore(false);
        return mEventRepository.getMaterialFileList4_0(
                mEventBean == null ? "" : mEventBean.getApplyInfoVo().getApplyinstId(),
                mEventBean == null ? "" : mEventBean.getIteminstList().get(0).getIteminstId()
        );
//        if ("并联".equals(mIsSeriesApproval)) {
//            return mEventRepository.getMaterialListA(mEventBean == null ? "" : mEventBean.getIteminstList().get(0).getIteminstId());
//        } else {
//            return mEventRepository.getMaterialListB(mEventBean == null ? "" : mEventBean.getIteminstList().get(0).getIteminstId());
//        }


    }

    @Override
    public void onItemClick(View itemView, int position, MaterialListBean data) {

        Intent intent = MaterialDetailActivity.getIntent(getActivity(), data, mEventBean.getApplyInfoVo().getApplyinstId(), data.getAttMatinstId());
        getActivity().startActivityForResult(intent,FLASH_RESULT_CODE);//跳到材料详情
//        List<MaterialListBean.FileListBean> fileList = data.getFileList();
//        List<FileBean> fileBeans = new ArrayList<>();
//        if (!ListUtil.isEmpty(fileList)) {
//            for (MaterialListBean.FileListBean fileListBean : fileList) {
//                FileBean fileBean = new FileBean();
//                fileBean.setId(fileListBean.getFileId());
//                fileBean.setUploaded(true);
//                fileBean.setName(fileListBean.getFileName());
//                //fileBean将该字段作为matinstId
//                fileBean.setMimeType(data.getAttMatinstId() == null ? "" : data.getAttMatinstId());
//                fileBeans.add(fileBean);
//            }
//        }
//        FormRecord formRecord = new FormRecord();
//        formRecord.setFormCode("code");
//        //先将appid作为 MatId
//        formRecord.setAppId(data.getMatId());
//        formRecord.setProcessInstanceId(data.getAttMatinstId());
//        if (getActivity() != null) {
//            int state = getActivity().getIntent().getIntExtra("state", EventState.HANDLING);
//            Intent intent = AccessoryActivity.getIntent(getContext(), EventState.HANDLING != state ? WorkflowState.STATE_NONE : WorkflowState.STATE_TODO, new ArrayList<>(fileBeans), formRecord);
//            startActivity(intent);
//        }


//        mEventRepository.getMaterialFileList(data.getAttMatinstId() == null ? "" : data.getAttMatinstId())
//                .map(listApiResult -> {
//                    List<FileBean> fileBeans = new ArrayList<>();
//                    if (listApiResult != null && listApiResult.isSuccess()) {
//
//                        List<Attachment> data1 = listApiResult.getData();
//                        for (Attachment attachment : data1) {
//                            FileBean fileBean = new FileBean();
//                            fileBean.setId(attachment.getFileId());
//                            fileBean.setUploaded(true);
//                            fileBean.setName(attachment.getFileName());
//                            //fileBean将该字段作为matinstId
//                            fileBean.setMimeType(data.getAttMatinstId() == null ? "" : data.getAttMatinstId());
//
//                            fileBeans.add(fileBean);
//                        }
//                    }
//
//                    return fileBeans;
//                })
//                .subscribe(listApiResult -> {
//                    FormRecord formRecord = new FormRecord();
//                    formRecord.setFormCode("code");
//                    //先将appid作为 MatId
//                    formRecord.setAppId(data.getMatId());
//                    formRecord.setProcessInstanceId(data.getAttMatinstId());
//                    if (getActivity() != null) {
//                        int state = getActivity().getIntent().getIntExtra("state", EventState.HANDLING);
//                        Intent intent = AccessoryActivity.getIntent(getContext(), EventState.HANDLING != state ? WorkflowState.STATE_NONE : WorkflowState.STATE_TODO, new ArrayList<>(listApiResult), formRecord);
//                        startActivity(intent);
//                    }
//                });
    }


    @Subscribe
    public void receive(EventInfoBean eventBean) {
        mEventBean = eventBean;
        if (refresh_layout != null) {
            loadDatas(true);
        }
    }

    @Override
    public boolean onItemLongClick(View itemView, int position, MaterialListBean data) {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLASH_RESULT_CODE && resultCode == FLASH_RESULT_CODE) {
            loadDatas(true);
        }
    }
}