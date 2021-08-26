package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;

import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialListBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ResultGoodsMaterialBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.model.FileBean;
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
import java.util.Map;

/**
 * 结果物补充物材料详情
 */
public class ResultGoodsMaterialsDetailActivity extends AwFormActivity {
    public static final String MATERIAL_BEAN = "MATERIAL_BEAN";
    ResultGoodsMaterialBean resultGoodsMaterialBean;
    String applyinstId;
    String matinstId;
    private boolean isEdit = true;//是否编辑状态
    EventRepository eventRepository;

    public static Intent getIntent(Context ctx, ResultGoodsMaterialBean data, String applyinstId,String matinstId) {
        Intent intent = new Intent(ctx, ResultGoodsMaterialsDetailActivity.class);
        intent.putExtra(MATERIAL_BEAN, data);
        intent.putExtra("applyinstId", applyinstId);
        intent.putExtra("matinstId", matinstId);
        return intent;
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        resultGoodsMaterialBean = (ResultGoodsMaterialBean) getIntent().getSerializableExtra(MATERIAL_BEAN);
        applyinstId = getIntent().getStringExtra("applyinstId");
        matinstId = getIntent().getStringExtra("matinstId");
        mFormCode = AwFormConfig.FORM_GCJS_RESULT_GOODS_MATERIAL_DETAIL;
        if (mRecord == null) {
            mRecord = new FormRecord();
        }
        Map<String, String> map = new HashMap<>();
        map.put("materialName", resultGoodsMaterialBean.getIteminstName());

        mRecord.setFormCode(mFormCode);
        mRecord.setValues(map);

        mFormState = FormState.STATE_READ;
        mTitleText = "结果物补充材料附件";

        eventRepository = new EventRepository();
    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        if (resultGoodsMaterialBean != null) {
            ArrayList<FileBean> fileBeans = new ArrayList<>();
            if (!ListUtil.isEmpty(resultGoodsMaterialBean.getItemHisResultFileList())) {
                for (ResultGoodsMaterialBean.ItemHisResultFileListBean fileListBean : resultGoodsMaterialBean.getItemHisResultFileList()) {
                    FileBean fileBean = new FileBean();
                    fileBean.setId(fileListBean.getFileId());
                    fileBean.setUploaded(true);
                    if (fileListBean.getIsActive()!=null && fileListBean.getIsActive().equals("0")){
                        String source="申请人上传结果物";
                        fileBean.setName(fileListBean.getFileName()+"("+source+")");
                    }else {
                        String source="系统出具结果物";
                        fileBean.setName(fileListBean.getFileName()+"("+source+")");
                    }
                    try {
                        fileBean.setSize(Long.parseLong(fileListBean.getFileSize()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //fileBean将该字段作为matinstId
                    fileBean.setMimeType(resultGoodsMaterialBean.getIteminstId() == null ? "" : resultGoodsMaterialBean.getIteminstId());
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
                        ToastUtil.longToast(ResultGoodsMaterialsDetailActivity.this, "已上传电子件无法删除");
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
                                ToastUtil.shortToast(ResultGoodsMaterialsDetailActivity.this, "文件下载失败");
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
            Intent intent = WatchImageOrPdfActivity.newIntent(this, imagePaths);
            startActivity(intent);
            return;
        }
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
