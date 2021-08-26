package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ResultGoodsBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ZjzzBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ZjzzTypeBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.AGMultiSpinner;
import com.augurit.agmobile.common.view.combineview.AgFilePicker;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.agmobile.common.view.common.fileview.FileOpenViewActivity;
import com.augurit.common.common.form.AwFormActivity;
import com.augurit.common.common.form.AwFormConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;

import org.apache.commons.collections4.MapUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.augurit.agmobile.agwater5.gcjs.eventlist.view
 * Created by sdb on 2019/4/12  16:03.
 * Desc：
 */

public class ZjzzAddActivity extends AwFormActivity implements WidgetListener {
    public static final String ZJZZ_TYPE_BEAN = "ZJZZ_TYPE_BEAN";

    private String applyinstId;
    private String isSeriesApproval;
    private String iteminstId;
    private String taskId;
    private ZjzzBean mData;
    private EventRepository mEventRepository;
    private ZjzzTypeBean zjzzTypeBean;//所有的证件证照
    private ArrayList<FileBean> mFileBeans;
    private boolean isEdit;

    @Override
    protected void initArguments() {
        super.initArguments();
        mFormCode = AwFormConfig.FORM_GCJS_ZJZZ_ADD;
        mEventRepository = new EventRepository();
        applyinstId = getIntent().getStringExtra("applyinstId");
        isSeriesApproval = getIntent().getStringExtra("isSeriesApproval");
        iteminstId = getIntent().getStringExtra("iteminstId");
        taskId = getIntent().getStringExtra("taskId");
        zjzzTypeBean = (ZjzzTypeBean) getIntent().getSerializableExtra(ZJZZ_TYPE_BEAN);
        mData = (ZjzzBean) getIntent().getSerializableExtra("data");
        if (mData != null) {
            List<ZjzzBean.AttFilesBean> attFiles = mData.getAttFiles();
            mFileBeans = new ArrayList<>();
            for (ZjzzBean.AttFilesBean attFile : attFiles) {
                FileBean fb = new FileBean();
                fb.setId(attFile.getFileId());
                try {
                    fb.setSize(Long.parseLong(attFile.getFileSize()));
                }catch (Exception e){

                }
                fb.setName(attFile.getFileName());
                fb.setUploaded(true);
                fb.setPath("0");
                fb.setMimeType(attFile.getFileType());
                mFileBeans.add(fb);
            }

            mData.setAttFiles(null);
            Gson gson = new Gson();
            String toJson = gson.toJson(mData);
            Map<String, String> map = gson.fromJson(toJson, new TypeToken<Map<String, String>>() {
            }.getType());
            mRecord = new FormRecord();
            mRecord.setFormCode(mFormCode);
            mFormState = FormState.STATE_READ;
            mRecord.setValues(map);

//            if ("电子".equals(mData.getDocTypeName())) {
//                getFiles();
//            }

        }
    }


    //    @Override
//    protected boolean showShareButton() {
//        return true;
//    }
    //获取相关的file
    private void getFiles() {
//        List<FileBean> fileBeanList = new ArrayList<>();
//        FileBean fileBean = new FileBean();
//        fileBean.setId(mData.getCertinstId());
//        fileBean.setUploaded(true);
//        fileBean.setName(mData.getCertinstName());
//        fileBean.setPath("0");
//        fileBeanList.add(fileBean);
        mFormPresenter.setWidgetValue("file", mFileBeans);
//        AgFilePicker filePicker = (AgFilePicker) mFormPresenter.getWidget("file").getView();
//        filePicker.setValue(mFileBeans);

    }

    @Override
    protected void initView() {
        super.initView();

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
                                ToastUtil.shortToast(ZjzzAddActivity.this, "文件下载失败");
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
    protected void onFormLoaded() {
        super.onFormLoaded();

        mFormPresenter.addWidgetListener(this);

        btn_save.setVisibility(View.GONE);
        title_bar.setMoreButtonAction(v -> {
            if (mData != null) {
                if (!"1".equals(mData.getIsOnlyRead())) {//1为只读
                    if (!isEdit) {
                        isEdit = true;
                        title_bar.setTitleText("编辑证件证照");
                        title_bar.setMoreButtonText("取消");
                        mFormPresenter.setFormState(FormState.STATE_EDIT);
                        mFormPresenter.getWidget("matId").setEnable(false);
                        view_divider_btn_container.setVisibility(View.VISIBLE);
                        btn_container.setVisibility(View.VISIBLE);
                        btn_submit.setVisibility(View.VISIBLE);
                    } else {
                        isEdit = false;
                        title_bar.setMoreButtonText("编辑");
                        title_bar.setTitleText("证件证照");
                        mFormPresenter.setFormState(FormState.STATE_READ);
                        view_divider_btn_container.setVisibility(View.GONE);
                        btn_container.setVisibility(View.GONE);
                        btn_submit.setVisibility(View.GONE);
                    }
                }else{//只读
                    title_bar.setTitleText("证件证照");
                    mFormPresenter.setFormState(FormState.STATE_READ);

                    view_divider_btn_container.setVisibility(View.GONE);
                    btn_container.setVisibility(View.GONE);
                    btn_submit.setVisibility(View.GONE);
                }
            }
        });
        AGMultiSpinner certIdSpinner = (AGMultiSpinner) mFormPresenter.getWidget("matId").getView();
        if (mData != null) {
            title_bar.setMoreButtonText("编辑");
            title_bar.setTitleText("证件证照");
            btn_submit.setVisibility(View.GONE);

            getFiles();

            mFormPresenter.getWidget("matId").setEnable(false);
            ArrayList<IDictItem> dicts = new ArrayList<>();
            DictItem dictItem = new DictItem();
            if (zjzzTypeBean!=null){
                for (ZjzzTypeBean.CertListBean certListBean : zjzzTypeBean.getCertList()) {
                    if (mData.getMatId().equals(certListBean.getMatId())) {
                        dictItem.setValue(certListBean.getMatId());
                        dictItem.setLabel(certListBean.getCertName());
                        dictItem.setId(certListBean.getCertId());
                        break;
                    }
                }
                for (ResultGoodsBean resultGoodsBean : zjzzTypeBean.getResultsList()) {
                    if (mData.getMatId().equals(resultGoodsBean.getMatId())) {
                        dictItem.setValue(resultGoodsBean.getMatId());
                        dictItem.setLabel(resultGoodsBean.getMatName());
                        dictItem.setId(resultGoodsBean.getCertId());
                        break;
                    }
                }
            }else {
                dictItem.setValue(mData.getMatId());
                dictItem.setLabel(mData.getCertinstName());
                dictItem.setId(mData.getCertId());
            }
            dicts.add(dictItem);
            certIdSpinner.initData(dicts);
            certIdSpinner.setValue(mData.getMatId());

        } else {
            //加载证照类型
            initType();
        }

        AgFilePicker agFilePicker = (AgFilePicker) mFormPresenter.getWidget("file").getView();
        agFilePicker.setOnFilePickerListener(new AgFilePicker.OnFilePickerClickListener() {
            @Override
            public void onDeleteButtonClick(int i, FileBean fileBean) {

                AlertDialog dialog = new AlertDialog.Builder(ZjzzAddActivity.this)
                        .setTitle("提示:")
                        .setMessage("是否删除")
                        .setNegativeButton("取消", (dialog1, which) -> {
                        })
                        .setPositiveButton("确定", (dialog12, which) -> {
                            if ("0".equals(fileBean.getPath())) {

                                Map<String, String> map = new HashMap<>();
                                map.put("detailIds", fileBean.getId());//文件id
                                map.put("matinstId", mData.getMatinstId());//事项id
                                HttpUtil.getInstance(AwUrlManager.serverUrl()).delete(GcjsUrlConstant.DELETE_PWPF_MATERIAL_ATTACHMENT, map)
                                        .subscribe(s -> {
                                            if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                                                ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                                                }.getType());
                                                if (apiResult != null && apiResult.isSuccess()) {
                                                    ToastUtil.shortToast(ZjzzAddActivity.this, "删除成功");

                                                    agFilePicker.removeFile(i);

                                                    if (!ListUtil.isEmpty(mFileBeans) && mFileBeans.contains(fileBean)) {
                                                        mFileBeans.remove(fileBean);
//                                                    mFormPresenter.setWidgetValue("file", mFileBeans);
                                                        BaseFormWidget widget = mFormPresenter.getWidget("file");
                                                        //((AgFilePicker) widget.getView()).setMaxLimit(mFileBeans.size() + 1);
                                                    }

                                                } else {
                                                    ToastUtil.shortToast(ZjzzAddActivity.this, "删除失败");
                                                }
                                            }

                                        });
                            } else {
                                agFilePicker.removeFile(i);
                            }
                        })

                        .create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.agmobile_red));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);


            }

            @Override
            public void onItemClick(View view, int i, FileBean fileBean) {
                downFile(fileBean);
            }
        });

        initOrg();
        if (mData != null) {//设置完dictitem之后需要重新设置一遍value
            mFormPresenter.getWidget("matId").setValue(mData.getMatId());
            mFormPresenter.setWidgetValue("issueOrgId", mData.getIssueOrgId());
        }
    }

    private void initType() {
        double projScale = zjzzTypeBean.getProjScale();
        mFormPresenter.getWidget("projScale").setValue(projScale+"");
        List<ZjzzTypeBean.CertListBean> certList = zjzzTypeBean.getCertList();
        List<ResultGoodsBean> resultsList = zjzzTypeBean.getResultsList();
        List<IDictItem> certs = new ArrayList<>();
        if (!ListUtil.isEmpty(certList)) {
            for (ZjzzTypeBean.CertListBean certListBean : certList) {
                DictItem dictItem = new DictItem();
                dictItem.setValue(certListBean.getMatId());
                dictItem.setLabel(certListBean.getCertName());
                dictItem.setId(certListBean.getCertId());
                certs.add(dictItem);
            }
        }
        if (!ListUtil.isEmpty(resultsList)) {
            for (ResultGoodsBean resultGoodsBean : resultsList) {
                DictItem dictItem = new DictItem();
                dictItem.setValue(resultGoodsBean.getMatId());
                dictItem.setLabel(resultGoodsBean.getMatName());
                dictItem.setId(resultGoodsBean.getCertId());
                certs.add(dictItem);
            }
        }

        AGMultiSpinner certIdSpinner = (AGMultiSpinner) mFormPresenter.getWidget("matId").getView();
        certIdSpinner.initData(certs);

    }

    private void initOrg() {
        List<ZjzzTypeBean.OpuOmOrgListBean> opuOmOrgList = zjzzTypeBean.getOpuOmOrgList();
        if (!ListUtil.isEmpty(opuOmOrgList)) {
            List<IDictItem> orgs = new ArrayList<>();
            for (ZjzzTypeBean.OpuOmOrgListBean opuOmOrgListBean : opuOmOrgList) {
                DictItem dictItem = new DictItem();
                dictItem.setValue(opuOmOrgListBean.getOrgId());
                dictItem.setLabel(opuOmOrgListBean.getOrgName());
                orgs.add(dictItem);
            }
            AGMultiSpinner orgIdSpinner = (AGMultiSpinner) mFormPresenter.getWidget("issueOrgId").getView();
            orgIdSpinner.initData(orgs);
        }
    }

    @Override
    protected void submit() {
        if (mFormPresenter.validate()) {

            HashMap<String, String> widgetValues = mFormPresenter.getWidgetValues();
            HashMap<String, ArrayList<FileBean>> files = mFormPresenter.getFiles();
            widgetValues.put("applyinstId", applyinstId == null ? "" : applyinstId);
            if (mData != null && mData.getMatinstId() != null) {
                widgetValues.put("matinstId", mData.getMatinstId());
            }
            widgetValues.put("iteminstId", iteminstId == null ? "" : iteminstId);
            widgetValues.put("taskId", taskId == null ? "" : taskId);
            AGMultiSpinner certIdSpinner = (AGMultiSpinner) mFormPresenter.getWidget("matId").getView();
            DictItem dictitem = (DictItem) certIdSpinner.getCurrentSelected().get(0);
            String certId = dictitem.getId();
            widgetValues.put("certId", certId == null ? "" : certId);
            Map<String, List<FileBean>> files1 = new HashMap<>();
            ArrayList<FileBean> fileList = new ArrayList<>();
            if (!MapUtils.isEmpty(files)) {
                ArrayList<FileBean> widgetFileList = files.get("file");
                widgetValues.put("attCount", ListUtil.isEmpty(widgetFileList) ? "0" : widgetFileList.size() + "");
                if (!ListUtil.isEmpty(widgetFileList)) {
                    for (FileBean fileBean : widgetFileList) {
                        if (!"0".equals(fileBean.getPath()) && !fileBean.getPath().startsWith("http://")) {
                            fileList.add(fileBean);
                            break;
                        }
                    }
                }
            }
            files1.put("files", fileList);
            if (mData != null) {//证照实例id不为空则是更新
                widgetValues.put("certinstId", mData.getCertinstId());
            }
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在提交，请等待");
            progressDialog.show();


            HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).postWithFileBySameName(GcjsUrlConstant.SAVE_ZJZZ, widgetValues, files1)
                    .subscribe(s -> {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                                ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                                }.getType());
                                if (apiResult != null && apiResult.isSuccess()) {
                                    ToastUtil.shortToast(ZjzzAddActivity.this, "提交成功");
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    ToastUtil.shortToast(ZjzzAddActivity.this, "提交失败");
                                }
                            }
                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                        ToastUtil.shortToast(ZjzzAddActivity.this, "网络错误，提交失败");
                    });

        }

    }


    @Override
    public void onValueChange(BaseFormWidget baseFormWidget, Object o, @Nullable Object o1, boolean b) {
        if ("attrTwo".equals(baseFormWidget.getElement().getElementCode())) {
            if ("纸质".equals(baseFormWidget.getValue())) {
                mFormPresenter.setWidgetVisible("attCount", true);
                mFormPresenter.setWidgetVisible("file", false);
            } else {
                mFormPresenter.setWidgetVisible("attCount", false);
                mFormPresenter.setWidgetVisible("file", true);
            }
        }
    }
}
