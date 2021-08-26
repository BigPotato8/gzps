package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClfjBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.ClfjFileAndFoldListAdapter;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.ClfjListAdapter;
import com.augurit.agmobile.agwater5.gcjs.util.FilePreviewUtil;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.common.Callback2;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.filepicker.ui.WEUIFilePickerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import static android.app.Activity.RESULT_OK;
import static com.augurit.agmobile.common.view.filepicker.ui.WEUIFilePickerActivity.RESULT_FILE_PATH;

/**
 * @description 申办材料附件Fragment
 * @date: 20190402
 * @author: sdb
 */
public class FjpwpfFragment extends BaseViewListFragment<ClfjBean.FilesBean> {

    private String isSeriesApproval;
    private EventBean mEventBean;
    private EventRepository mEventRepository;
    private FilePreviewUtil mFilePreviewUtil;
    private final String DIR_ID = "pwpf_2018_augurit";
    public final static int FJ_PWPF_REQUEST = 2080;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    public static FjpwpfFragment getInstance(String isSeriesApproval) {
        FjpwpfFragment baseInfoFragment = new FjpwpfFragment();
        Bundle bundle = new Bundle();
        bundle.putString("isSeriesApproval", isSeriesApproval);
        baseInfoFragment.setArguments(bundle);
        return baseInfoFragment;
    }


    @Override
    protected void initArguments() {
        super.initArguments();
        mEventRepository = new EventRepository();
        mFilePreviewUtil = new FilePreviewUtil();
        isSeriesApproval = getArguments().getString("isSeriesApproval");
    }

    @Override
    protected BaseViewListAdapter<ClfjBean.FilesBean> initAdapter() {
        return new ClfjListAdapter(getContext());
    }

    @Override
    protected void initView() {
        super.initView();

        btn_add_floating.setVisibility(View.VISIBLE);
        btn_add_floating.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WEUIFilePickerActivity.class);
            intent.putExtra(WEUIFilePickerActivity.SELECT_LIMIT, 1);
            getActivity().startActivityForResult(intent, FJ_PWPF_REQUEST);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FJ_PWPF_REQUEST) {
            if (resultCode == RESULT_OK) {
                ArrayList<FileBean> fileBeans = data.getParcelableArrayListExtra(RESULT_FILE_PATH);
                Map<String, String> params = new HashMap<>();
                params.put("tableName", "AEA_HI_APPLYINST");
                params.put("pkName", "APPLYINST_ID");
                params.put("recordId", mEventBean == null ? "" : mEventBean.getApplyinstId());

                Map<String, FileBean> filesMap = new HashMap<>();
                if (!ListUtil.isEmpty(fileBeans)) {
                    filesMap.put("files", fileBeans.get(0));
                }
                HttpUtil.getInstance(AwUrlManager.serverUrl()).postWithFile(GcjsUrlConstant.UPLOAD_ATTACHMENT,
                        params, filesMap)
                        .doOnSubscribe(disposable -> ProgressDialogUtil.showProgressDialog(getActivity(), "正在上传", false))
                        .subscribe(s -> {
                            for (FileBean fileBean : fileBeans) {
                                fileBean.setUploaded(false);
                            }
//                            mAdapter.addData(fileBeans);
//                            resetView();
//                            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                            ToastUtil.shortToast(getActivity(), "上传成功");
//                            finish();
                            ProgressDialogUtil.dismissProgressDialog();
                        }, throwable -> {
                            ProgressDialogUtil.dismissProgressDialog();
                            throwable.printStackTrace();

                        });

            }
        }
    }


    @Override
    protected Observable<ApiResult<List<ClfjBean.FilesBean>>> loadDatas(int page, int rows, Map filterParams) {
        refresh_layout.setNoMoreData(true);
        String type = "并联".equals(isSeriesApproval) ? "0" : "1";
        return mEventRepository.getClfjList("pwpf_2018_augurit", type, mEventBean.getApplyinstId())
                ;
    }

    @Override
    public void onItemClick(View itemView, int position, ClfjBean.FilesBean data) {
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

        FormRecord formRecord = new FormRecord();
        formRecord.setFormCode("code");
        //先将appid作为 MatId
        formRecord.setAppId(data.getLinkId());
        List<FileBean> list = new ArrayList<>();
        FileBean fb = new FileBean();
        fb.setName(data.getAttName());
        fb.setId(data.getDetailId());
        fb.setUploaded(true);
        list.add(fb);
//        if (getActivity() != null) {
//            int state = getActivity().getIntent().getIntExtra("state", EventState.HANDLING);
//            Intent intent = AccessoryActivity.getIntent(getContext(), EventState.HANDLING != state ? WorkflowState.STATE_NONE : WorkflowState.STATE_TODO, new ArrayList<>(list), formRecord);
//            startActivity(intent);
//        }

        mFilePreviewUtil.downFile(fb,getActivity());
    }

    @Override
    public boolean onItemLongClick(View itemView, int position, ClfjBean.FilesBean data) {
        mFilePreviewUtil.deleteFile(getActivity(), mAdapter, position, data, new Callback2<ApiResult>() {
            @Override
            public void onSuccess(ApiResult apiResult) {

            }

            @Override
            public void onFail(Exception e) {

            }
        });
        return false;
    }


    @Subscribe
    public void receive(EventBean eventBean) {

        mEventBean = eventBean;
        loadDatas(true);

    }
}
