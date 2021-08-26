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
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.Attachment;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfTypeBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.common.im.timchat.model.CustomMessageType;
import com.augurit.agmobile.common.im.timchat.model.message.CustomJsonMessage;
import com.augurit.agmobile.common.im.timchat.model.message.CustomMessage;
import com.augurit.agmobile.common.im.timchat.model.message.Message;
import com.augurit.agmobile.common.im.timchat.ui.RepostActivity;
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

import org.apache.commons.collections4.MapUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * com.augurit.agmobile.agwater5.gcjs.eventlist.view
 * Created by sdb on 2019/4/12  16:03.
 * Desc：
 */

public class PwpfAddActivity extends AwFormActivity implements WidgetListener {
    public final String LIMIT_TIME = "2300-12-31";
    private String applyinstId;
    private String isSeriesApproval;
    private String iteminstId;
    private String taskId;
    private PwpfBean.ItemMatinstBean mData;
    private EventRepository mEventRepository;
    private ArrayList<FileBean> mFileBeans;
    private boolean isEdit;
    private List<PwpfTypeBean> pwpfTypeBeanList;
    @Override
    protected void initArguments() {
        super.initArguments();
        mFormCode = AwFormConfig.FORM_GCJS_PWPF_ADD;
        mEventRepository = new EventRepository();
        applyinstId = getIntent().getStringExtra("applyinstId");
        isSeriesApproval = getIntent().getStringExtra("isSeriesApproval");
        iteminstId = getIntent().getStringExtra("iteminstId");
        taskId = getIntent().getStringExtra("taskId");
        pwpfTypeBeanList = (List<PwpfTypeBean>) getIntent().getSerializableExtra("typeList");
        mData = (PwpfBean.ItemMatinstBean) getIntent().getSerializableExtra("data");
        if (mData != null) {
            Map<String, String> map = new HashMap();
            map.put("officialDocNo", mData.getOfficialDocNo());
            map.put("officialDocTitle", mData.getOfficialDocTitle());
            map.put("officialDocDueDate", mData.getOfficialDocDueDate());
            map.put("matId", mData.getDocTypeName());
            map.put("attCount", mData.getDocCount() + "");
            map.put("memo", mData.getMemo());
            mRecord = new FormRecord();
            mRecord.setFormCode(mFormCode);
            mFormState = FormState.STATE_READ;
            mRecord.setValues(map);

//            if ("电子".equals(mData.getDocTypeName())) {
//                getFiles();
//            }

        }
    }

    private void setFiles() {
        if (!ListUtil.isEmpty(mData.getAttFiles())) {
            List<FileBean> fileBeans = new ArrayList<>();
            for (PwpfBean.ItemMatinstBean.AttFilesBean attFile : mData.getAttFiles()) {
                FileBean fileBean = new FileBean();
                fileBean.setId(attFile.getFileId());
                fileBean.setUploaded(true);
                fileBean.setName(attFile.getFileName());
                fileBean.setMimeType(mData.getMatinstId() == null ? "" : mData.getMatinstId());
                fileBean.setPath("0");
                fileBeans.add(fileBean);
            }
            mFormPresenter.setWidgetValue("file", mFileBeans);
            BaseFormWidget widget = mFormPresenter.getWidget("file");
        }
    }

    //    @Override
//    protected boolean showShareButton() {
//        return true;
//    }
    //获取相关的file
    private void getFiles() {

        mEventRepository.getPwpfFileList(mData.getMatinstId())
                .map(listApiResult -> {
                    List<FileBean> fileBeans = new ArrayList<>();
                    if (listApiResult.isSuccess()) {

                        List<Attachment> data1 = listApiResult.getData();
                        for (Attachment attachment : data1) {
                            FileBean fileBean = new FileBean();
                            fileBean.setId(attachment.getFileId());
                            fileBean.setUploaded(true);
                            fileBean.setName(attachment.getFileName());
                            try {
                                fileBean.setSize(Long.parseLong(attachment.getFileSize()));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            fileBean.setMimeType(mData.getMatinstId() == null ? "" : mData.getMatinstId());
                            fileBeans.add(fileBean);
                        }
                    }
                    return fileBeans;
                })
                .subscribe(listApiResult -> {

                    mFileBeans = new ArrayList<>(listApiResult);
                    for (FileBean fileBean : mFileBeans) {
                        fileBean.setPath("0");
                    }
                    mFormPresenter.setWidgetValue("file", mFileBeans);
                    BaseFormWidget widget = mFormPresenter.getWidget("file");
                    //((AgFilePicker) widget.getView()).setMaxLimit(mFileBeans.size() + 1);
                },throwable -> throwable.printStackTrace());
    }

    @Override
    protected void initView() {
        super.initView();

//        mFloatingShareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomJsonMessage customJsonMessage = new CustomJsonMessage();
//                customJsonMessage.setMsgType(CustomMessageType.TYPE_FORM);
//                customJsonMessage.setTitle("批文批复");
//                customJsonMessage.setUrl("com.augurit.agmobile.agwater5.gcjs.eventlist.view.PwpfAddActivity");
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("isSeriesApproval", isSeriesApproval == null ? "" : isSeriesApproval);
//                    jsonObject.put("applyinst", mData == null ? "" : applyinstId);
//                    jsonObject.put("iteminst", mData == null ? "" : iteminstId);
//                    jsonObject.put("taskId", taskId == null ? "" : taskId);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                customJsonMessage.setDataStr(jsonObject.toString());
//                Message message = CustomMessage.newJsonMessage(customJsonMessage.toString());
//                startActivity(RepostActivity.newIntent(PwpfAddActivity.this, message));
//            }
//        });
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
                                ToastUtil.shortToast(PwpfAddActivity.this, "文件下载失败");
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
                if (!isEdit) {
                    isEdit = true;
                    title_bar.setTitleText("编辑批文批复");
                    title_bar.setMoreButtonText("取消");
                    mFormPresenter.setFormState(FormState.STATE_EDIT);
                    view_divider_btn_container.setVisibility(View.VISIBLE);
                    mFormPresenter.getWidget("matId").setEnable(false);
                    btn_container.setVisibility(View.VISIBLE);
                    btn_submit.setVisibility(View.VISIBLE);
                } else {
                    isEdit = false;
                    title_bar.setMoreButtonText("编辑");
                    title_bar.setTitleText("批文批复");
                    mFormPresenter.setFormState(FormState.STATE_READ);

                    view_divider_btn_container.setVisibility(View.GONE);
                    btn_container.setVisibility(View.GONE);
                    btn_submit.setVisibility(View.GONE);
                }
            }
        });

        AGMultiSpinner matIdSpinner = (AGMultiSpinner) mFormPresenter.getWidget("matId").getView();
        if (mData != null) {
            title_bar.setMoreButtonText("编辑");
            title_bar.setTitleText("批文批复");
            btn_submit.setVisibility(View.GONE);

            mFormPresenter.getWidget("matId").setEnable(false);
            ArrayList<IDictItem> dicts = new ArrayList<>();
            DictItem dictItem = new DictItem();
            dictItem.setValue(mData.getMatId());
            dictItem.setLabel(mData.getMatinstName());
            dicts.add(dictItem);
            matIdSpinner.initData(dicts);
            matIdSpinner.setValue(mData.getMatId());
            if ("纸质".equals(mData.getDocTypeName())) {
                mFormPresenter.setWidgetVisible("attCount", true);
                mFormPresenter.setWidgetVisible("file", false);
            } else {
                mFormPresenter.setWidgetVisible("attCount", false);
                mFormPresenter.setWidgetVisible("file", true);
            }
            getFiles();

            if (LIMIT_TIME.equals(mData.getOfficialDocDueDate())) {//长期有效
                mFormPresenter.setWidgetVisible("officialDocDueDate", false);
                mFormPresenter.setWidgetValue("isLimit","是");
            }else{
                mFormPresenter.setWidgetVisible("officialDocDueDate", true);
                mFormPresenter.setWidgetValue("isLimit","否");
            }

        }else{//是新增，初始化批文类型
            if (pwpfTypeBeanList!=null) {
                ArrayList<IDictItem> dicts = new ArrayList<>();
                for (PwpfTypeBean pwpfTypeBean : pwpfTypeBeanList) {
                    DictItem dictItem = new DictItem();
                    dictItem.setValue(pwpfTypeBean.getMatId());
                    dictItem.setLabel(pwpfTypeBean.getMatName());
                    dicts.add(dictItem);
                }
                matIdSpinner.initData(dicts);
            }
        }

        AgFilePicker agFilePicker = (AgFilePicker) mFormPresenter.getWidget("file").getView();
        agFilePicker.setOnFilePickerListener(new AgFilePicker.OnFilePickerClickListener() {
            @Override
            public void onDeleteButtonClick(int i, FileBean fileBean) {

                AlertDialog dialog = new AlertDialog.Builder(PwpfAddActivity.this)
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
                                                    ToastUtil.shortToast(PwpfAddActivity.this, "删除成功");

                                                    agFilePicker.removeFile(i);

                                                    if (!ListUtil.isEmpty(mFileBeans) && mFileBeans.contains(fileBean)) {
                                                        mFileBeans.remove(fileBean);
//                                                    mFormPresenter.setWidgetValue("file", mFileBeans);
                                                        BaseFormWidget widget = mFormPresenter.getWidget("file");
                                                        //((AgFilePicker) widget.getView()).setMaxLimit(mFileBeans.size() + 1);
                                                    }

                                                } else {
                                                    ToastUtil.shortToast(PwpfAddActivity.this, "删除失败");
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


    }

    @Override
    protected void submit() {
        if (mFormPresenter.validate()) {

            HashMap<String, String> widgetValues = mFormPresenter.getWidgetValues();
            HashMap<String, ArrayList<FileBean>> files = mFormPresenter.getFiles();
            widgetValues.put("applyinstId", applyinstId == null ? "" : applyinstId);
            widgetValues.put("matinstId", mData == null ? "" : (mData.getMatinstId() == null ? "" : mData.getMatinstId()));
            //0并联1单项,iteminst事项实例, 并联时不用传
            widgetValues.put("iteminstId", "并联".equals(isSeriesApproval)||"0".equals(isSeriesApproval) ? "" : (iteminstId == null ? "" : iteminstId));
            widgetValues.put("taskId", taskId == null ? "" : taskId);
            Map<String, List<FileBean>> files1 = new HashMap<>();
            ArrayList<FileBean> fileList = new ArrayList<>();
            if (!MapUtils.isEmpty(files)) {
                ArrayList<FileBean> widgetFileList = files.get("file");
                widgetValues.put("attCount", ListUtil.isEmpty(widgetFileList)?"0":widgetFileList.size()+"");
                if (!ListUtil.isEmpty(widgetFileList)) {
                    for (FileBean fileBean : widgetFileList) {
                        if (!"0".equals(fileBean.getPath()) && !fileBean.getPath().startsWith("http://")) {
                            fileList.add(fileBean);
                        }
                    }
                }
            }
            //判断是否长期有效，是则填入limit_time
            if ("是".equals(widgetValues.get("isLimit"))) {
                widgetValues.put("officialDocDueDate",LIMIT_TIME);
            }else if("否".equals(widgetValues.get("isLimit"))) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                widgetValues.put("officialDocDueDate",format.format(new Date()));
            }
            files1.put("files",fileList);
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在提交，请等待");
            progressDialog.show();


            HttpUtil.getInstance(AwUrlManager.serverUrl()).postWithFileBySameName(mData == null ? GcjsUrlConstant.CREATE_PWPF : GcjsUrlConstant.EDIT_PWPF, widgetValues, files1)
                    .subscribe(s -> {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                                ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                                }.getType());
                                if (apiResult != null && apiResult.isSuccess()) {
                                    ToastUtil.shortToast(PwpfAddActivity.this, "提交成功");
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    ToastUtil.shortToast(PwpfAddActivity.this, "提交失败");
                                }
                            }
                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                        ToastUtil.shortToast(PwpfAddActivity.this, "网络错误，提交失败");
                    });

        }

    }


    @Override
    public void onValueChange(BaseFormWidget baseFormWidget, Object o, @Nullable Object item, boolean b) {
        if ("attrTwo".equals(baseFormWidget.getElement().getElementCode())) {
            if ("纸质".equals(baseFormWidget.getValue())) {
                mFormPresenter.setWidgetVisible("attCount", true);
                mFormPresenter.setWidgetVisible("file", false);
            } else {
                mFormPresenter.setWidgetVisible("attCount", false);
                mFormPresenter.setWidgetVisible("file", true);
            }
        }
        if (baseFormWidget.getElement().getElementCode().equals("isLimit")){//是否长期有效
            String value = ((DictItem) item).getValue();
            if("是".equals(value)){
                mFormPresenter.setWidgetVisible("officialDocDueDate", false);
            }else{
                mFormPresenter.setWidgetVisible("officialDocDueDate", true);
            }
        }

    }
}
