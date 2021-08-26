package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.Attachment;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialListBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.workflow.model.WorkflowState;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.AgFilePicker;
import com.augurit.agmobile.common.view.common.fileview.FileOpenViewActivity;
import com.augurit.common.common.form.AwFormActivity;
import com.augurit.common.common.form.AwFormConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * 材料详情界面，用于新增电子件，关联材料库
 */
public class MaterialDetailActivity extends AwFormActivity {
    public static final String MATERIAL_BEAN = "MATERIAL_BEAN";
    MaterialListBean materialListBean;
    String applyinstId;
    String matinstId;
    private boolean isEdit = true;//是否编辑状态
    EventRepository eventRepository;

    public static Intent getIntent(Context ctx, MaterialListBean data, String applyinstId, String matinstId) {
        Intent intent = new Intent(ctx, MaterialDetailActivity.class);
        intent.putExtra(MATERIAL_BEAN, data);
        intent.putExtra("applyinstId", applyinstId);
        intent.putExtra("matinstId", matinstId);
        return intent;
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        materialListBean = (MaterialListBean) getIntent().getSerializableExtra(MATERIAL_BEAN);
        applyinstId = getIntent().getStringExtra("applyinstId");
        matinstId = getIntent().getStringExtra("matinstId");
        mFormCode = AwFormConfig.FORM_GCJS_MATERIAL_DETAIL;
        if (mRecord == null) {
            mRecord = new FormRecord();
        }
        Map<String, String> map = new HashMap<>();
        map.put("matName", materialListBean.getMatName());
        if (materialListBean.getDuePaperCount() != null && materialListBean.getRealPaperCount() != null) {
            map.put("paperCount", materialListBean.getDuePaperCount() + "/" + materialListBean.getRealPaperCount());
        } else {
            map.put("paperCount", "0");
        }
        if (materialListBean.getDueCopyCount() != null && materialListBean.getRealCopyCount() != null) {
            map.put("copyCount", materialListBean.getDueCopyCount() + "/" + materialListBean.getRealCopyCount());
        } else {
            map.put("copyCount", "0");
        }
        if (materialListBean.getAttIsRequire()!=null) {//材料属性
            if (materialListBean.getAttIsRequire().equals("0")){
                map.put("attIsRequire","可选");
            }else {
                map.put("attIsRequire","必交");
            }
        }
        mRecord.setFormCode(mFormCode);
        mRecord.setValues(map);

        mFormState = FormState.STATE_READ;
        mTitleText = "材料附件";

        eventRepository = new EventRepository();
    }


    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        btn_save.setText("资料库");
        btn_submit.setText("上传");

        if (materialListBean != null) {
            title_bar.setMoreButtonText("编辑");
            title_bar.setMoreButtonAction(v -> {
                if (isEdit) {
                    isEdit = false;
                    title_bar.setTitleText("编辑材料附件");
                    title_bar.setMoreButtonText("取消");
                    mFormPresenter.setFormState(FormState.STATE_EDIT);
                    view_divider_btn_container.setVisibility(View.VISIBLE);
                    btn_container.setVisibility(View.VISIBLE);
                    btn_submit.setVisibility(View.VISIBLE);
                    btn_save.setVisibility(View.VISIBLE);
                } else {
                    isEdit = true;
                    title_bar.setMoreButtonText("编辑");
                    title_bar.setTitleText("材料附件");
                    mFormPresenter.setFormState(FormState.STATE_READ);
                    view_divider_btn_container.setVisibility(View.GONE);
                    btn_container.setVisibility(View.GONE);
                    btn_submit.setVisibility(View.GONE);
                    btn_save.setVisibility(View.GONE);
                }
            });
            ArrayList<FileBean> fileBeans = new ArrayList<>();
            if (!ListUtil.isEmpty(materialListBean.getFileList())) {
                for (MaterialListBean.FileListBean fileListBean : materialListBean.getFileList()) {
                    FileBean fileBean = new FileBean();
                    fileBean.setId(fileListBean.getFileId());
                    fileBean.setUploaded(true);
                    fileBean.setName(fileListBean.getFileName());
                    try {
                        fileBean.setSize(Long.parseLong(fileListBean.getFileSize()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //fileBean将该字段作为matinstId
                    fileBean.setMimeType(materialListBean.getAttMatinstId() == null ? "" : materialListBean.getAttMatinstId());
                    fileBean.setPath(AwUrlManager.getGcjsUrl().concat(GcjsUrlConstant.URL_FILE_DONMLOAD_4_0 + "/" + fileListBean.getFileId()));
                    fileBeans.add(fileBean);
                }
            }
            mFormPresenter.setWidgetValue("files", fileBeans);
            AgFilePicker agFilePicker = (AgFilePicker) mFormPresenter.getWidget("files").getView();
            agFilePicker.setMaxLimit(fileBeans.size() + 1);
            agFilePicker.setOnFilePickerListener(new AgFilePicker.OnFilePickerClickListener() {//设置预览
                @Override
                public void onDeleteButtonClick(int i, FileBean fileBean) {
                    if (i < fileBeans.size()) {
                        ToastUtil.longToast(MaterialDetailActivity.this, "已上传电子件无法删除");
                    } else {
                        agFilePicker.removeFile(i);//删除手机已选的最后一个
                    }
                }

                @Override
                public void onItemClick(View view, int i, FileBean fileBean) {
                    downFile(fileBean);
                }
            });
        }
    }

    @Override
    protected void submit() {
        ArrayList<FileBean> files = mFormPresenter.getFiles().get("files");
        ArrayList<FileBean> uploadFiles = new ArrayList<>();
        if (!ListUtil.isEmpty(files)) {
            for (FileBean fileBean : files) {
                if (!TextUtils.isEmpty(fileBean.getPath()) && !fileBean.getPath().startsWith("0") && !fileBean.getPath().startsWith("http://")) {
                    uploadFiles.add(fileBean);
                }
            }
        }

        if (!ListUtil.isEmpty(uploadFiles)) {//上传已选择文件
            Map<String, String> params = new HashMap<>();
            params.put("applyinstId", applyinstId);
            params.put("matinstId", matinstId);
            params.put("matId", materialListBean.getMatId());
            Map<String, List<FileBean>> map = new HashMap<>();
            map.put("file", uploadFiles);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在上传，请等待");
            progressDialog.show();

            eventRepository.uploadMaterialFiles4_0(params, map)
                    .subscribe(new Consumer<ApiResult>() {
                        @Override
                        public void accept(ApiResult apiResult) throws Exception {
                            progressDialog.dismiss();
                            if (apiResult.isSuccess()) {
                                ToastUtil.longToast(MaterialDetailActivity.this, "上传成功");
                                setResult(MaterialListFragment.FLASH_RESULT_CODE);
                                finish();
                            } else {
                                ToastUtil.longToast(MaterialDetailActivity.this, "上传失败");
                            }
                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                        progressDialog.dismiss();
                        ToastUtil.longToast(MaterialDetailActivity.this, "网络错误，上传失败");
                    });
        } else {
            ToastUtil.longToast(this, "无选择文件");
        }

    }

    @Override
    protected void save() {
        //跳转到材料库选择关联材料
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在获取材料库，请等待");
        progressDialog.show();

        eventRepository.getMaterialStore(applyinstId, materialListBean.getMatId())
                .subscribe(new Consumer<ApiResult<List<Attachment>>>() {
                    @Override
                    public void accept(ApiResult<List<Attachment>> apiResult) throws Exception {
                        progressDialog.dismiss();
                        if (apiResult.isSuccess()) {
                            if (!ListUtil.isEmpty(apiResult.getData())) {
                                ArrayList<FileBean> fileBeans = new ArrayList<>();
                                for (Attachment attachment : apiResult.getData()) {
                                    FileBean fileBean = new FileBean();
                                    fileBean.setId(attachment.getFileId());
                                    fileBean.setUploaded(true);
                                    fileBean.setName(attachment.getFileName());
                                    try {
                                        fileBean.setSize(Long.parseLong(attachment.getFileSize()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //fileBean将该字段作为matinstId
                                    fileBean.setMimeType(materialListBean.getAttMatinstId() == null ? "" : materialListBean.getAttMatinstId());
                                    fileBean.setPath(AwUrlManager.getGcjsUrl().concat(GcjsUrlConstant.URL_FILE_DONMLOAD_4_0 + "/" + attachment.getFileId()));
                                    fileBeans.add(fileBean);
                                }
                                FormRecord formRecord = new FormRecord();
                                formRecord.setFormCode("code");
                                //先将appid作为 MatId
                                formRecord.setAppId(materialListBean.getMatId());
                                formRecord.setProcessInstanceId(materialListBean.getAttMatinstId());
                                Intent intent = AccessoryActivity.getIntent(MaterialDetailActivity.this,
                                        WorkflowState.STATE_TODO,
                                        fileBeans,
                                        formRecord,
                                        materialListBean.getAttMatinstId() == null ? "" : materialListBean.getAttMatinstId());
                                startActivityForResult(intent, MaterialListFragment.FLASH_RESULT_CODE);
                            } else {
                                ToastUtil.longToast(MaterialDetailActivity.this, "材料库无材料");
                            }
                        } else {
                            ToastUtil.longToast(MaterialDetailActivity.this, "获取材料库列表失败");
                        }
                    }
                },throwable -> {
                    throwable.printStackTrace();
                    progressDialog.dismiss();
                });
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
                        .serverUrl(AwUrlManager.serverUrl())
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
                                ToastUtil.shortToast(MaterialDetailActivity.this, "文件下载失败");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== MaterialListFragment.FLASH_RESULT_CODE && resultCode== MaterialListFragment.FLASH_RESULT_CODE){
            setResult(MaterialListFragment.FLASH_RESULT_CODE);
            finish();
        }
    }
}
