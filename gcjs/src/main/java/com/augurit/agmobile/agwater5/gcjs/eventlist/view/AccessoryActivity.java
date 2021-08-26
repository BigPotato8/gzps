package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.AccessoryAdapter;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.busi.bpm.BpmInjection;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.workflow.model.WorkflowState;
import com.augurit.agmobile.busi.bpm.workflow.source.IWorkflowRepository;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.net.NetConnectionUtil;
import com.augurit.agmobile.common.lib.net.NetworkStateManager;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.common.fileview.FileOpenViewActivity;
import com.augurit.agmobile.common.view.filepicker.ui.WEUIFilePickerActivity;
import com.augurit.agmobile.common.view.loadinglayout.LoadingMaskLayout;
import com.augurit.agmobile.common.view.navigation.TitleBar;
import com.augurit.common.common.util.DialogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.augurit.agmobile.busi.bpm.workflow.view.ApprovalEditView.EXTRA_RESULT_ACCESSORY;
import static com.augurit.agmobile.common.view.filepicker.ui.WEUIFilePickerActivity.RESULT_FILE_PATH;

/**
 * 附件查看activity,增加，删除，查看，附件
 *
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agmobile5.bpm
 * @createTime 创建时间 ：2019/2/14
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AccessoryActivity extends AppCompatActivity implements NetworkStateManager.OnNetworkChangedListener {
    public static final String EXTRA_WORKFLOW_STATE = "extra_workflow_state";
    public static final String EXTRA_ACCESSORYS = "extra_accessory";
    public static final String EXTRA_FORMRECORD = "extra_form_record";
    public static final String SUFFIX_WORKFLOW = "_workflow";
    public static final String MATINST_ID = "MATINST_ID";

    private static final int ACCESSORY_REQUEST_CODE = 1020;

    private TitleBar mTitleBar;
    private View mErrorView;
    private RecyclerView mRecyclerView;
    private LinearLayout mBtnAdd;
    private LoadingMaskLayout maskLayout;
    private LinearLayout btnContainer;
    private Button btnUpload;

    private NetworkStateManager mNetworkStateManager;

    private Intent mIntent;
    private AccessoryAdapter mAdapter;
    private IWorkflowRepository mWorkflowRepository;
    private CompositeDisposable compositeDisposable;

    private int mWorkflowState;
    private ArrayList<FileBean> fileBeans;
    private FormRecord mFormRecord;
    private String downloadPath;
    private String tempPath;

    private String matinstId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bpm_activity_accessory);
        downloadPath = new FilePathUtil(getBaseContext()).getDownloadFilePath();
        mWorkflowRepository = BpmInjection.provideWorkflowRepository(this);
        compositeDisposable = new CompositeDisposable();
        tempPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        initView();
        ToastUtil.longToast(this,"点击查看材料，长按选择关联");
    }

    //必传参：fileBeans，其中FileBean的name和path必须有
    public static Intent getIntent(Context context, @WorkflowState int workflowState, ArrayList<FileBean> fileBeans, FormRecord formRecord, String matinstId) {
        Intent intent = new Intent(context, AccessoryActivity.class);
        intent.putExtra(EXTRA_WORKFLOW_STATE, workflowState);
        intent.putExtra(EXTRA_ACCESSORYS, fileBeans);
        intent.putExtra(EXTRA_FORMRECORD, formRecord);
        intent.putExtra(MATINST_ID, matinstId);
        return intent;
    }

    private void initView() {
        mIntent = getIntent();
        mWorkflowState = mIntent.getIntExtra(EXTRA_WORKFLOW_STATE, WorkflowState.STATE_NONE);
        fileBeans = mIntent.getParcelableArrayListExtra(EXTRA_ACCESSORYS);
        mFormRecord = (FormRecord) mIntent.getSerializableExtra(EXTRA_FORMRECORD);
        matinstId = mIntent.getStringExtra(MATINST_ID);
        mTitleBar = findViewById(R.id.title_bar);
        mErrorView = findViewById(R.id.view_net_error);
        mRecyclerView = findViewById(R.id.rv_recyclerView);
        mBtnAdd = findViewById(R.id.btn_add);
        btnContainer = findViewById(R.id.btn_container);
        btnUpload = findViewById(R.id.btn_upload);
        maskLayout = findViewById(R.id.mask_layout);
        maskLayout.setAction(getString(com.augurit.agmobile.busi.bpm.R.string.bpm_btn_reload), null);
        mAdapter = new AccessoryAdapter(R.layout.item_accessory_layout);
        mAdapter.setNewData(fileBeans);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mBtnAdd.setOnClickListener(v -> addAccessory());
        mTitleBar.setMoreButtonAction(v -> overDone());
        mTitleBar.setTitle("材料库");
        btnUpload.setOnClickListener(v -> uploadFiles());
        mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_content://长按item，弹框是否关联
                        FileBean fileBean = mAdapter.getItem(position);
                        DialogUtil.MessageBox(AccessoryActivity.this, "提示", "是否关联该材料？"
                                , (dialog, which) -> aboutMaterial(fileBean), (dialog, which) -> dialog.dismiss());
                        break;
                }
                return true;
            }
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_delete:
                    FileBean fileBean = mAdapter.getItem(position);
                    if (fileBean == null) {
                        return;
                    }
                    if (fileBean.isUploaded()) {
                        Map<String, String> params = new HashMap<>();
                        params.put("detailIds", fileBean.getId());
                        //fileBean将该字段作为matinstId
                        params.put("matinstId", fileBean.getMimeType());
                        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.DELETE_ATTACHMENT, params)
                                .doOnSubscribe(disposable -> ProgressDialogUtil.showProgressDialog(AccessoryActivity.this, "正在删除", false))
                                .subscribe(s -> {
                                    ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                                    }.getType());
                                    if (apiResult != null && apiResult.isSuccess()) {
                                        mAdapter.remove(position);
                                        resetView();
                                        ToastUtil.shortToast(AccessoryActivity.this, "删除成功");
                                    } else {
                                        ToastUtil.shortToast(AccessoryActivity.this, "删除失败");
                                    }
                                }, throwable -> {
                                    throwable.printStackTrace();
                                    ToastUtil.shortToast(AccessoryActivity.this, "删除失败");
                                }, ProgressDialogUtil::dismissProgressDialog);


                        compositeDisposable.add(subscribe);
                    } else {
                        mAdapter.remove(position);
                        resetView();
                    }
                    break;
                case R.id.ll_content://点击item，打开文件
                    FileBean item = mAdapter.getItem(position);
                    if (item == null) {
                        return;
                    }
                    downFile(item);
//                    if (item.isUploaded()) {
//                        String dPath = downloadPath + "/" + item.getName();
//                        File file = new File(dPath);
//                        if (file.exists() && file.isFile()) {
//                            //直接打开文件
//                            previewFile(item, dPath);
//                        } else {
//                            //下载文件，并打开文件
//                            ProgressDialogUtil.showProgressDialog(AccessoryActivity.this, "正在下载附件...", false);
//                            AuthDownloadManager.getInstance()
//                                    .serverUrl(AwUrlManager.serverUrl())
//                                    .downLoadPath(GcjsUrlConstant.URL_FILE_DONMLOAD_4_0 + "/" + item.getId())
//                                    .savePath(downloadPath)
//                                    .saveName(item.getName())
//                                    .Download(new AuthDownloadListener() {
//                                        @Override
//                                        public void success() {
//                                            ProgressDialogUtil.dismissProgressDialog();
//                                            //直接打开文件
//                                            previewFile(item, dPath);
//                                        }
//
//                                        @Override
//                                        public void failed() {
//                                            //跳出对话框，提示无证书
//                                            ProgressDialogUtil.dismissProgressDialog();
//                                            ToastUtil.shortToast(AccessoryActivity.this, "文件下载失败");
//                                        }
//                                    });
//                        }
//                    } else {
//                        String path = item.getPath();
//                        previewFile(item, path);
//                    }
                    break;
            }
        });
        TextView tv_error = mErrorView.findViewById(R.id.tv_error);
        tv_error.setText(R.string.bpm_view_net_problem);
        if (!NetConnectionUtil.isConnected(this)) {
            mErrorView.setVisibility(View.VISIBLE);
        }
        mNetworkStateManager = new NetworkStateManager();
        mNetworkStateManager.registerNetworkChangeListener(this, this);
        initData();

        if (ListUtil.isEmpty(fileBeans)) {
            maskLayout.showEmpty("暂无附件", null);
        }
    }

    private void aboutMaterial(FileBean fileBean) {
        Map<String, String> param = new HashMap<>();
        param.put("matinstId", matinstId);
        param.put("fileIds", fileBean.getId());
        //fileIds,matinstId
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在关联，请等待");
        progressDialog.show();

        HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).post(GcjsUrlConstant.POST_EVENT_MATERIAL_FILE_LINK, param)
                .map(s -> {
                    ApiResult data =
                            new Gson().fromJson(s, new TypeToken<ApiResult>() {
                            }.getType());
                    return data;
                })
                .subscribe(new Consumer<ApiResult>() {
                    @Override
                    public void accept(ApiResult apiResult) throws Exception {
                        progressDialog.dismiss();
                        if (apiResult.isSuccess()) {//成功关联
                            ToastUtil.longToast(AccessoryActivity.this,"关联成功");
                            setResult(MaterialListFragment.FLASH_RESULT_CODE);
                            finish();
                        }else{
                            ToastUtil.longToast(AccessoryActivity.this,"关联失败");
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialog.dismiss();
                    ToastUtil.longToast(AccessoryActivity.this,"网络错误，关联失败");
                });
    }

//    private void previewFile(FileBean item, String dPath) {
//        //如果是图片
//        if (dPath.toLowerCase().endsWith(".png") || dPath.toLowerCase().endsWith(".jpeg") || dPath.toLowerCase().endsWith(".jpg")) {
//            ArrayList<String> imagePaths = new ArrayList<>();
//            imagePaths.add(dPath);
//            //Intent intent = ImageViewActivity.newIntent(AccessoryActivity.this, 0, imagePaths);
//            Intent intent = WatchImageOrPdfActivity.newIntent(AccessoryActivity.this, imagePaths);
//            startActivity(intent);
//            return;
//        }
//
////        else if (dPath.toLowerCase().endsWith(".docx")) {
////            try {
////                String mineType = FileUtils.getMimeType(dPath);
////                Intent intent = new Intent(Intent.ACTION_VIEW);
////                intent.addCategory(Intent.CATEGORY_DEFAULT);
////                FileUtils.setFlags(intent);
////                Uri uri = FileUtils.getUri(dPath, this);
////                intent.setDataAndType(uri, mineType);
////                startActivity(intent);
////            } catch (Exception e) {
////                ToastUtil.shortToast(this, getString(R.string.validate_check_file_open_fail));
////            }
////
////            return;
////        }
//
//        FileOpenViewActivity.getIntent(AccessoryActivity.this, dPath, tempPath, item.getName());
//
//    }

    private void resetView() {
        btnContainer.setVisibility(View.GONE);
        if (mAdapter.getItemCount() != 0) {
            maskLayout.hide();
            for (FileBean fileBean : mAdapter.getData()) {
                if (!fileBean.isUploaded()) {
                    btnContainer.setVisibility(View.VISIBLE);
                    break;
                }
            }
        } else {
            maskLayout.showEmpty("暂无附件", null);
        }
    }

    private void addAccessory() {
        Intent intent = new Intent(this, WEUIFilePickerActivity.class);
        intent.putExtra(WEUIFilePickerActivity.SELECT_LIMIT, 1);
        startActivityForResult(intent, ACCESSORY_REQUEST_CODE);
    }

    private void overDone() {
        Intent mIntent = getIntent();
        mIntent.putExtra(EXTRA_RESULT_ACCESSORY, (ArrayList<FileBean>) mAdapter.getData());
        setResult(RESULT_OK, mIntent);
        finish();
    }

    //上传附件
    private void uploadFiles() {
        List<FileBean> data = mAdapter.getData();
        Iterator<FileBean> iterator = data.iterator();
        List<FileBean> toUpload = new ArrayList<>();
        while (iterator.hasNext()) {
            FileBean next = iterator.next();
            if (!next.isUploaded()) {
                toUpload.add(next);
            }
        }
        Disposable subscribe = Observable.fromIterable(toUpload)
                .flatMap((Function<FileBean, ObservableSource<ApiResult<String>>>) fileBean -> {
                    File file = new File(fileBean.getPath());
                    return mWorkflowRepository.uploadAccessory(file, mFormRecord.getFormCode() + SUFFIX_WORKFLOW, mFormRecord.getRecordId(), mFormRecord.getRecordId(), "");
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> ProgressDialogUtil.showProgressDialog(AccessoryActivity.this, "正在上传", false))
                .subscribe(apiResults -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    for (ApiResult<String> apiResult : apiResults) {
                        if (!apiResult.isSuccess()) {
                            ToastUtil.shortToast(AccessoryActivity.this, "上传失败");
                            return;
                        }
                    }
                    ToastUtil.shortToast(AccessoryActivity.this, "上传成功");
                    finish();
                }, throwable -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();

                });
        compositeDisposable.add(subscribe);
    }

    private void getFilesOnline() {
        mBtnAdd.setVisibility(View.GONE);//暂时先隐藏
        //根据id获取附件列表
//        maskLayout.showLoading("正在加载");
//        Disposable subscribe = mWorkflowRepository.getAccessoryInfos(mFormRecord.getFormCode(), "", mFormRecord.getRecordId(), "")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(apiResult -> {
//                    ArrayList<FileBean> fileBeans = new ArrayList<>();
//                    for (Attachment info : apiResult.getData()) {
//                        FileBean fileBean = new FileBean();
//                        fileBean.setUploaded(true);
//                        fileBean.setId(info.getDetailId());
//                        fileBean.setPath(BpmUrlManager.urlBpmRest() + "/" + UrlConstant.DOWNLOAD_ACCESSORY_FILE + "?detailId=" + info.getDetailId());
//                        fileBean.setName(info.getAttName());
//                        fileBean.setIcon(FileTypeUtils.getFileTypeBySuffix(info.getAttFormat()).getIcon());
//                        fileBeans.add(fileBean);
//                    }
//                    mAdapter.setNewData(fileBeans);
//                    resetView();
//                    mBtnAdd.setVisibility(View.VISIBLE);
//                }, throwable -> {
//                    btnContainer.setVisibility(View.GONE);
//                    maskLayout.showError("重新加载", v -> getFilesOnline());
//                });
//        compositeDisposable.add(subscribe);
    }


    private void initData() {
        switch (mWorkflowState) {
            case WorkflowState.STATE_TODO:
//                getFilesOnline();
                mBtnAdd.setVisibility(View.GONE);

//                mAdapter.setNewData(fileBeans);
//                resetView();
                break;
            case WorkflowState.STATE_DONE:
            case WorkflowState.STATE_FINISHED:
//                mBtnAdd.setVisibility(View.VISIBLE);
//                if(ListUtil.isEmpty(fileBeans)){
//                    //请求网络加载
//                    getFilesOnline();
//                }else {
//                    mAdapter.setNewData(fileBeans);
//                    resetView();
//                }
//                mBtnAdd.setVisibility(View.VISIBLE);
                mBtnAdd.setVisibility(View.GONE);
                break;
            default:
                mBtnAdd.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACCESSORY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                ArrayList<FileBean> fileBeans = data.getParcelableArrayListExtra(RESULT_FILE_PATH);
                Map<String, String> params = new HashMap<>();
                params.put("tableName", "AEA_HI_ITEM_MATINST");
                params.put("pkName", "MATINST_ID");
                params.put("matinstId", mFormRecord.getProcessInstanceId() == null ? "" : mFormRecord.getProcessInstanceId());
                params.put("matId", mFormRecord.getAppId() == null ? "" : mFormRecord.getAppId());

                Map<String, FileBean> filesMap = new HashMap<>();
                if (!ListUtil.isEmpty(fileBeans)) {
                    filesMap.put("files", fileBeans.get(0));
                }
                HttpUtil.getInstance(AwUrlManager.serverUrl()).postWithFile(GcjsUrlConstant.UPLOAD_ATTACHMENT,
                        params, filesMap)
                        .doOnSubscribe(disposable -> ProgressDialogUtil.showProgressDialog(AccessoryActivity.this, "正在上传", false))
                        .subscribe(s -> {
                            for (FileBean fileBean : fileBeans) {
                                fileBean.setUploaded(false);
                            }
                            mAdapter.addData(fileBeans);
                            resetView();
                            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                            ToastUtil.shortToast(AccessoryActivity.this, "上传成功");
                            finish();
                        }, throwable -> {
                            ProgressDialogUtil.dismissProgressDialog();
                            throwable.printStackTrace();

                        });


            }
        }
    }

    @Override
    public void onNetworkChange(boolean isConnectedBefore, boolean isConnectedNow) {
        if (mErrorView == null) return;
        if (!isConnectedBefore && isConnectedNow) {
            mErrorView.setVisibility(View.GONE);
        } else if (isConnectedBefore && !isConnectedNow) {
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }


    public void downFile(FileBean item) {
        if (item == null) {
            return;
        }
        String downloadPath = new FilePathUtil(getBaseContext()).getDownloadFilePath();
        if (item.isUploaded()) {
            String dPath = downloadPath + "/" + item.getName();
            File file = new File(dPath);
            if (file.exists() && file.isFile()) {
                //直接打开文件
                previewFile(item, dPath);
            } else {
                //下载文件，并打开文件
                ProgressDialogUtil.showProgressDialog(this, "正在下载附件...", false);
                AuthDownloadManager.getInstance()
                        .serverUrl(AwUrlManager.getGcjsUrl())
                        .downLoadPath(GcjsUrlConstant.URL_FILE_DONMLOAD_4_0 + "/" + item.getId())
                        .savePath(downloadPath)
                        .saveName(item.getName())
                        .Download(new AuthDownloadListener() {
                            @Override
                            public void success() {
                                ProgressDialogUtil.dismissProgressDialog();
                                //直接打开文件
                                previewFile(item, dPath);
                            }

                            @Override
                            public void failed() {
                                //跳出对话框，提示无证书
                                ProgressDialogUtil.dismissProgressDialog();
                                ToastUtil.shortToast(AccessoryActivity.this, "文件下载失败");
                            }
                        });
            }
        } else {
            String path = item.getPath();
            previewFile(item, path);
        }
    }


    private void previewFile(FileBean item, String dPath) {
        String tempPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //如果是图片
        if (dPath.toLowerCase().endsWith(".png") || dPath.toLowerCase().endsWith(".jpeg") || dPath.toLowerCase().endsWith(".jpg")) {
            ArrayList<String> imagePaths = new ArrayList<>();
            imagePaths.add(dPath);
            //Intent intent = ImageViewActivity.newIntent(AccessoryActivity.this, 0, imagePaths);
            Intent intent = WatchImageOrPdfActivity.newIntent(this, imagePaths);
            startActivity(intent);
            return;
        }

//        else if(dPath.toLowerCase().endsWith(".docx") ){
//            try {
//                String mineType = FileUtils.getMimeType(dPath);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                FileUtils.setFlags(intent);
//                Uri uri = FileUtils.getUri(dPath, this);
//                intent.setDataAndType(uri, mineType);
//                startActivity(intent);
//            } catch (Exception e) {
//                ToastUtil.shortToast(this, getString(R.string.validate_check_file_open_fail));
//            }
//            return;
//        }
        FileOpenViewActivity.getIntent(this, dPath, tempPath, item.getName());
    }

}
