package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Intent;
import android.os.Environment;
import android.view.View;

import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.TscxBean;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.view.combineview.AgFilePicker;
import com.augurit.agmobile.common.view.common.fileview.FileOpenViewActivity;
import com.augurit.common.common.form.AwFormActivity;
import com.augurit.common.common.form.AwFormConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 特殊程序详情activity
 */
public class TscxDetailActivity extends AwFormActivity {
    public static final String TSCX_INFO = "TSCX_INFO";

    private TscxBean.SpecialListBean mBean;
    private List<FileBean> startList;
    private ArrayList<Object> endList;

    @Override
    protected void initArguments() {
        super.initArguments();
        mBean = (TscxBean.SpecialListBean) getIntent().getSerializableExtra(TSCX_INFO);
        if (mBean!=null){
            List<TscxBean.SpecialListBean.MatBean> specialStartMatList = mBean.getSpecialStartMatList();
            startList = new ArrayList<>();
            if (specialStartMatList!=null) {
                for (TscxBean.SpecialListBean.MatBean matBean : specialStartMatList) {
                    FileBean fileBean = new FileBean();
                    fileBean.setId(matBean.getDetailId());
                    fileBean.setName(matBean.getAttName());
                    fileBean.setCreateTime(matBean.getCreateTime());
                    fileBean.setUploaded(true);
                    try {
                        fileBean.setSize(Long.parseLong(matBean.getAttSize()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    fileBean.setPath(AwUrlManager.getGcjsUrl().concat(GcjsUrlConstant.URL_FILE_DONMLOAD_4_0 + "/" + matBean.getDetailId()));
                    startList.add(fileBean);
                }
                mBean.setSpecialStartMatList(null);
            }

            List<TscxBean.SpecialListBean.MatBean> specialEndMatList = mBean.getSpecialEndMatList();
            endList = new ArrayList<>();
            if (specialEndMatList!=null) {
                for (TscxBean.SpecialListBean.MatBean matBean : specialEndMatList) {
                    FileBean fileBean = new FileBean();
                    fileBean.setId(matBean.getDetailId());
                    fileBean.setName(matBean.getAttName());
                    fileBean.setCreateTime(matBean.getCreateTime());
                    try {
                        fileBean.setSize(Long.parseLong(matBean.getAttSize()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //fileBean.setPath(AwUrlManager.getGcjsUrl().concat(GcjsUrlConstant.URL_FILE_DONMLOAD_4_0 + "/" + matBean.getDetailId()));
                    endList.add(fileBean);
                }
                mBean.setSpecialEndMatList(null);
            }

            mRecord = new FormRecord();
            mRecord.setFormCode(AwFormConfig.FORM_GCJS_TSCX_DETAIL);

            Gson gson = new Gson();
            String toJson = gson.toJson(mBean);
            Map<String, String> map = gson.fromJson(toJson, new TypeToken<Map<String, String>>() {
            }.getType());
            mRecord.setValues(map);
        }
        mFormState = FormState.STATE_READ;
//        mTitleText = "特殊程序";
    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        mFormPresenter.setWidgetValue("specialStartMatList",startList);
        mFormPresenter.setWidgetValue("specialEndMatList",endList);

        AgFilePicker agFilePicker = (AgFilePicker) mFormPresenter.getWidget("specialStartMatList").getView();
        AgFilePicker agFilePicker2 = (AgFilePicker) mFormPresenter.getWidget("specialEndMatList").getView();
        AgFilePicker.OnFilePickerClickListener onImagePickerListener = new AgFilePicker.OnFilePickerClickListener() {
            @Override
            public void onDeleteButtonClick(int i, FileBean fileBean) {

            }

            @Override
            public void onItemClick(View view, int i, FileBean fileBean) {
                downFile(fileBean);
            }
        };
        agFilePicker.setOnFilePickerListener(onImagePickerListener);
        agFilePicker2.setOnFilePickerListener(onImagePickerListener);
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
                                ToastUtil.shortToast(TscxDetailActivity.this, "文件下载失败");
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

        FileOpenViewActivity.getIntent(this, dPath, tempPath, item.getName());
    }
}
